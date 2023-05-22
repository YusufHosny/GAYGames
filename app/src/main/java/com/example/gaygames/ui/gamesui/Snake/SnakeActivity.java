package com.example.gaygames.ui.gamesui.Snake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
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
    private RelativeLayout relative_layout;
    private SwipeListener SwipeListener;
    private LinkedList<Tile> Snake;
    private Direction nextDirection;
    private TextView ShowSwipe;
    private SnakeGrid Grid;
    private GridLayout gridLayout;
    private ScheduledFuture<?> f;
    private TextView tv;

    int Score;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);
        relative_layout = findViewById(R.id.constraint_lyato);
        gridLayout = findViewById(R.id.griddy_layout);
        tv = findViewById(R.id.textView2);

        SwipeListener = new SwipeListener(relative_layout,this);
        // Initialize grid
        Grid = new SnakeGrid(20,20);
        Score=0;
        tv.setText("Score: "+Score);
        // Initialize snake
        Snake = new LinkedList<>();
        Snake.add(new SnakeTile(Grid.getSnakeGrid().length-1,Grid.getInitialXPosition()));
        Snake.add(new SnakeTile(Grid.getSnakeGrid().length-2,Grid.getInitialXPosition()));
        Snake.add(new SnakeTile(Grid.getSnakeGrid().length-3,Grid.getInitialXPosition()));
        runOnUiThread(() -> { for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                ImageView imageView = new ImageView(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width =0;
                params.height = 0;
                params.columnSpec = GridLayout.spec(j, 1f);
                params.rowSpec = GridLayout.spec(i, 1f);
                imageView.setLayoutParams(params);
                gridLayout.addView(imageView);

                String X = Grid.getSnakeGrid()[i][j].revealTile();
                if (X.equals("Empty"))
                    imageView.setImageResource(R.drawable.grass);
                if (X.equals("Snake"))
                    imageView.setImageResource(R.drawable.black);
                if (X.equals("Fruit"))
                    imageView.setImageResource(R.drawable.apple);
            }
        }});


        //Snake initially goes up
        nextDirection=Direction.Up;

        int deltaT=100;

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            f = executor.scheduleAtFixedRate(this::updateBoard, 5 * deltaT, deltaT, TimeUnit.MILLISECONDS);
        }

    @SuppressLint("SetTextI18n")
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
            Score++;
            tv.setText("Score: "+Score);
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
                ImageView imageView = (ImageView) gridLayout.getChildAt(i * 20 + x);
                if (Grid.getSnakeGrid()[i][x].revealTile().equals("Snake")){
                    runOnUiThread(() -> imageView.setImageResource(R.drawable.black));
                }
                else if (Grid.getSnakeGrid()[i][x].revealTile().equals("Fruit")){
                    runOnUiThread(() -> imageView.setImageResource(R.drawable.apple));


                }
                else if (Grid.getSnakeGrid()[i][x].revealTile().equals("Empty")){

                    runOnUiThread(() -> imageView.setImageResource(R.drawable.grass));
                }
            }

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