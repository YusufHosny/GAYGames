package com.example.gaygames.ui.gamesui.Snake;


import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.Log;

import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gaygames.R;
import com.example.gaygames.ui.gamesui.general.gamePopUpMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import games.Snake.Direction;
import games.Snake.EmptyTile;
import games.Snake.SnakeGrid;
import games.Snake.SnakeTile;
import games.Snake.SwipeListener;
import games.Snake.Tile;
import games.general.UserData;

public class SnakeActivity extends AppCompatActivity {
    private LinkedList<Tile> Snake;

    private Direction nextDirection,nextOppositeDirection,currentDirection,currentOppositeDirection;

    private SnakeGrid Grid;
    private GridLayout gridLayout;
    private ScheduledFuture<?> f;
    private TextView scoreTV;

    int Score, HighScore;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);
        RelativeLayout relative_layout = findViewById(R.id.constraint_lyato);
        gridLayout = findViewById(R.id.griddy_layout);
        scoreTV = findViewById(R.id.scoreTV);

        new SwipeListener(relative_layout, this);

        // Initialize grid
        Grid = new SnakeGrid(20,20);
        Score=0;
        HighScore = Score;
        scoreTV.setText("Score: "+Score);

        gamePopUpMenu.setLeaderboardActivity(SnakeLeaderboardActivity.class);

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
        currentDirection=Direction.Up;
        currentOppositeDirection=Direction.Down;
        nextDirection=Direction.Up;
        nextOppositeDirection=Direction.Down;

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
        //x
        if (checkIncomingTile().revealTile().equals("Fruit")){
            Grid.getSnakeGrid()[newRow][newCol]=new SnakeTile(newRow,newCol);
            Snake.add(new SnakeTile(newRow,newCol));
            Grid.generateFruitTile();
            Score++;
            scoreTV.setText("Score: " + Score);
        }

        else if (checkIncomingTile().revealTile().equals("Snake")){
            f.cancel(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.constraint_lyato, new gamePopUpMenu()).commit();
            System.out.println("Game Over");
            gameDone();
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
        if (currentOppositeDirection!=nextDirection){
            currentDirection = nextDirection;
            currentOppositeDirection=nextOppositeDirection;
            if(currentDirection== Direction.Left)
                return checkOutOfBounds(lastRow,lastCol-1,lastRow,Grid.getSnakeGrid()[0].length-1);
            else if(currentDirection== Direction.Right)
                return checkOutOfBounds(lastRow,lastCol+1,lastRow,0);
            else if ((currentDirection== Direction.Up))
                return checkOutOfBounds(lastRow-1,lastCol,Grid.getSnakeGrid().length-1,lastCol);
            else
                return checkOutOfBounds(lastRow+1,lastCol,0,lastCol);
        }
            currentDirection = nextOppositeDirection;
            currentOppositeDirection = nextDirection;
            nextDirection = currentDirection;
            nextOppositeDirection=currentOppositeDirection;

            return this.checkIncomingTile();

    }

    public Tile checkOutOfBounds(int newRow, int newCol, int exRow, int exCol){
        try {
            return Grid.getSnakeGrid()[newRow][newCol];
        }
        catch(IndexOutOfBoundsException e){
            return Grid.getSnakeGrid()[exRow][exCol];
        }}

    public void setDirection(Direction direction,Direction opposite) {
        nextDirection = direction;
        nextOppositeDirection = opposite;
    }


    public void gameDone() {
        // check if high score and if it is update leaderboard
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest sendHighscore = new StringRequest( Request.Method.GET,
                "https://studev.groept.be/api/a22pt107/addScoreSnake/" + UserData.accountID + "/" + Score,
                response -> {},
                error -> Log.e("sendHighscoreSnake", error.getLocalizedMessage(), error));


        StringRequest getHighscore = new StringRequest( Request.Method.GET,
                "https://studev.groept.be/api/a22pt107/getHighestPersonalScoreSnake/" + UserData.accountID,
                response -> {
                    try {
                        JSONArray responseArray = new JSONArray(response);
                        JSONObject object = responseArray.getJSONObject(0);

                        int prevHighscore = object.getInt("score");

                        if(prevHighscore < Score) {
                            requestQueue.add(sendHighscore);
                        }

                    } catch(JSONException e) {
                        Log.e( "snakeGetScore", e.getMessage(), e );
                        requestQueue.add(sendHighscore);
                    }
                },
                error -> Log.e( "snakeGetScore", error.getLocalizedMessage(), error )
        );

        requestQueue.add(getHighscore);
    }

    @Override
    public void onBackPressed(){
        f.cancel(true);
        super.onBackPressed();
    }
}