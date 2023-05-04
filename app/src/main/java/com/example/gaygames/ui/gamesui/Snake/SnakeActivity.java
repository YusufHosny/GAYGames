package com.example.gaygames.ui.gamesui.Snake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gaygames.R;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import games.Snake.Direction;
import games.Snake.EmptyTile;
import games.Snake.SnakeGrid;
import games.Snake.SnakeTile;
import games.Snake.Tile;
import games.Snake.SwipeListener;

public class SnakeActivity extends AppCompatActivity {
    RelativeLayout relative_layout;
    SwipeListener SwipeListener;
    LinkedList<Tile> Snake;
    Direction nextDirection;
    TextView ShowSwipe;
    SnakeGrid Grid;
    ScheduledFuture<?> f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);
        relative_layout = findViewById(R.id.relative_layout);
        ShowSwipe = findViewById(R.id.textView2);
        SwipeListener = new SwipeListener(relative_layout,this,ShowSwipe);
        // Initialize grid
        Grid = new SnakeGrid(10,10);
        // Initialize snake
        Snake = new LinkedList<>();
        Snake.add(new SnakeTile(Grid.getSnakeGrid().length-1,Grid.getInitialXPosition()));
        Snake.add(new SnakeTile(Grid.getSnakeGrid().length-2,Grid.getInitialXPosition()));
        Snake.add(new SnakeTile(Grid.getSnakeGrid().length-3,Grid.getInitialXPosition()));
        //Snake initially goes up
        nextDirection=Direction.Up;

        int deltaT=500;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        f = executor.scheduleAtFixedRate(this::updateBoard, 5 * deltaT, deltaT, TimeUnit.MILLISECONDS);

    }

    public void updateBoard(){
        int newRow = checkIncomingTile().getRow();
        int newCol = checkIncomingTile().getCol();
        int firstRow = Snake.getFirst().getRow();
        int firstCol = Snake.getFirst().getCol();
        //
        if (checkIncomingTile().revealTile().equals("Fruit")){
            Grid.getSnakeGrid()[newRow][newCol]=new SnakeTile(newRow,newCol);
            Snake.add(new SnakeTile(newRow,newCol));
            Grid.generateFruitTile();
        }

        else if (checkIncomingTile().revealTile().equals("Snake")){
            System.out.println("Game Over");
        }
        else {
            Grid.getSnakeGrid()[newRow][newCol]=new SnakeTile(newRow,newCol);
            Snake.add(new SnakeTile(newRow,newCol));
            Grid.getSnakeGrid()[firstRow][firstCol]= new EmptyTile(firstRow,firstCol);
            Snake.removeFirst();
        }

        for (int i=0;i<Grid.getSnakeGrid().length;i++){
            for (int x=0;x<Grid.getSnakeGrid()[0].length;x++){
                if (Grid.getSnakeGrid()[i][x].revealTile().equals("Snake")){
                    System.out.print("1");
                }
                else if (Grid.getSnakeGrid()[i][x].revealTile().equals("Fruit")){
                    System.out.print("F");
                }
                else if (Grid.getSnakeGrid()[i][x].revealTile().equals("Empty")){
                    System.out.print("0");
                }
            }
            System.out.println(" ");
        }
    }
    public Tile checkIncomingTile(){
        int lastRow = Snake.getLast().getRow();
        int lastCol = Snake.getLast().getCol();
        if(nextDirection== Direction.Left)
            return checkOutOfBounds(lastRow,lastCol-1,lastRow,Grid.getSnakeGrid()[0].length-1);
        else if(nextDirection== Direction.Right)
            return checkOutOfBounds(lastRow,lastCol+1,lastRow,0);
        else if ((nextDirection== Direction.Up))
            return checkOutOfBounds(lastRow-1,lastCol,Grid.getSnakeGrid().length-1,lastCol);
        else
            return checkOutOfBounds(lastRow+1,lastCol,0,lastCol);
    }

    public Tile checkOutOfBounds(int newRow, int newCol, int exRow, int exCol){
        try {
            return Grid.getSnakeGrid()[newRow][newCol];
        }
        catch(IndexOutOfBoundsException e){
            return Grid.getSnakeGrid()[exRow][exCol];
        }}

    public void setDirection(Direction direction) {
        nextDirection = direction;
    }
}