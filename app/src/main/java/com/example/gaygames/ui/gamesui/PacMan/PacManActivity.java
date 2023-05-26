package com.example.gaygames.ui.gamesui.PacMan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaygames.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import games.PacMan.Ghost;
import games.PacMan.PMBoard;
import games.PacMan.PacMan;

public class PacManActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private int xPosition,yPosition;
    int[][] Board = PMBoard.getPMBoard();
    private TextView scoreText;
    private ImageButton leftButton,rightButton,upButton,downButton;
    private ScheduledFuture<?> f,f1;

    private Ghost redGhost;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pac_man);
        gridLayout = findViewById(R.id.PacManGridLayout);

        scoreText = findViewById(R.id.scoreText);
        scoreText.setText("Score " + 0);

        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        upButton = findViewById(R.id.upButton);
        downButton = findViewById(R.id.downButton);

        // Spawning Pacman at the center of the map
        PacMan.spawnPacMan();
        redGhost = new Ghost(1,1,2);
        // Printing out board with Grid Layout of imageViews
        runOnUiThread(() ->
        { for (int i = 0; i < Board.length; i++) {
            for (int j = 0; j < Board[0].length; j++) {
                ImageView imageView = new ImageView(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width =0;
                params.height = 0;
                params.columnSpec = GridLayout.spec(j, 1f);
                params.rowSpec = GridLayout.spec(i, 1f);
                imageView.setLayoutParams(params);
                imageView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                gridLayout.addView(imageView);

                if (Board[i][j]==0) {
                    imageView.setImageResource(R.drawable.pacmansc);
                }
                else if (Board[i][j]==1){
                    imageView.setImageResource(R.drawable.bluew);
                }
                else if (Board[i][j]==2){
                    imageView.setImageResource(R.drawable.redghost);
                }
                else if (Board[i][j]==6){
                    imageView.setImageResource(R.drawable.pmopenmouthright);
                }
                else if (Board[i][j]==9){
                    imageView.setImageResource(R.drawable.blackbackground);
                }
            }
        }});

        // Scheduled Executor to periodically update the UI with the method updateBoardUI()
        int deltaT=200;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        f = executor.scheduleAtFixedRate(this::updateBoardUI, 5 * deltaT, deltaT, TimeUnit.MILLISECONDS);
        int DeltaT=500;
        f1 = executor.scheduleAtFixedRate(this::moveGhost, 5 * DeltaT, DeltaT, TimeUnit.MILLISECONDS);
    }

    @SuppressLint("SetTextI18n")
    public void updateBoardUI(){
        scoreText.setText("Score " + PacMan.getScore());

        // Retrieve old position of PacMan
        int oldXPosition = PacMan.getxPosition();
        int oldYPosition = PacMan.getyPosition();

        // Move PacMan based on button held
        if (rightButton.isPressed()){
            ImageView imageView1 = (ImageView) gridLayout.getChildAt((oldYPosition *Board[0].length) + oldXPosition);
            runOnUiThread(() ->imageView1.setImageResource(R.drawable.blackbackground));
            PacMan.updatePosition(PacMan.getxPosition() + 1 , PacMan.getyPosition());
            ImageView imageView2 = (ImageView) gridLayout.getChildAt((PacMan.getyPosition() *Board[0].length) + PacMan.getxPosition());
            runOnUiThread(() ->imageView2.setImageResource(R.drawable.pmopenmouthright));

        }
        if (leftButton.isPressed()){
            ImageView imageView1 = (ImageView) gridLayout.getChildAt((oldYPosition *Board[0].length) + oldXPosition);
            runOnUiThread(() ->imageView1.setImageResource(R.drawable.blackbackground));
            PacMan.updatePosition(PacMan.getxPosition() - 1, PacMan.getyPosition());
            ImageView imageView2 = (ImageView) gridLayout.getChildAt((PacMan.getyPosition() *Board[0].length) + PacMan.getxPosition());
            runOnUiThread(() ->imageView2.setImageResource(R.drawable.pmopenmouthleft));

        }

        if (upButton.isPressed()){
            ImageView imageView1 = (ImageView) gridLayout.getChildAt((oldYPosition *Board[0].length) + oldXPosition);
            runOnUiThread(() ->imageView1.setImageResource(R.drawable.blackbackground));
            PacMan.updatePosition(PacMan.getxPosition() , PacMan.getyPosition() - 1);
            ImageView imageView2 = (ImageView) gridLayout.getChildAt((PacMan.getyPosition() *Board[0].length) + PacMan.getxPosition());
            runOnUiThread(() ->imageView2.setImageResource(R.drawable.pmopenmouthup));

        }

        if (downButton.isPressed()){
            ImageView imageView1 = (ImageView) gridLayout.getChildAt((oldYPosition *Board[0].length) + oldXPosition);
            runOnUiThread(() ->imageView1.setImageResource(R.drawable.blackbackground));
            PacMan.updatePosition(PacMan.getxPosition() , PacMan.getyPosition() + 1);
            ImageView imageView2 = (ImageView) gridLayout.getChildAt((PacMan.getyPosition() * Board[0].length) + PacMan.getxPosition());
            runOnUiThread(() ->imageView2.setImageResource(R.drawable.pmopenmouthdown));
        }
    }

    public void moveGhost(){
        // Move Red Ghost

        redGhost.moveByShortestPath();

        ImageView imageView2 = (ImageView) gridLayout.getChildAt((redGhost.getY() *Board[0].length) + redGhost.getX());
        runOnUiThread(() ->imageView2.setImageResource(R.drawable.redghost));

    }

}