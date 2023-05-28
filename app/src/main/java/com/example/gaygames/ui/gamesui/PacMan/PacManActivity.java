package com.example.gaygames.ui.gamesui.PacMan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gaygames.R;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import games.PacMan.Ghost;
import games.PacMan.PMBoard;
import games.PacMan.PacMan;

public class PacManActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private int xPosition,yPosition;
    private int[][] Board;

    private TextView scoreText;
    private ImageButton leftButton,rightButton,upButton,downButton;

    private PMBoard pmBoard;
    private ImageView HP1,HP2,HP3;
    private ScheduledFuture<?> f,f1;

    private Ghost redGhost,blueGhost,yellowGhost,pinkGhost;

    private PacMan pacman;

    private ArrayList<Ghost> Ghosts;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pac_man);
        gridLayout = findViewById(R.id.PacManGridLayout);
        // Views
        scoreText = findViewById(R.id.scoreText);
        scoreText.setText("Score " + 0);

        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        upButton = findViewById(R.id.upButton);
        downButton = findViewById(R.id.downButton);

        HP1 = findViewById(R.id.HP1);
        HP2 = findViewById(R.id.HP2);
        HP3 = findViewById(R.id.HP3);

        // create board
        pmBoard = new PMBoard();

        Board = pmBoard.getPMBoard();
        // Spawning Pacman and Ghosts at the center of the map
        pacman = new PacMan(pmBoard);
        pacman.spawnPacMan();

        redGhost = new Ghost(12,11,2, pacman, pmBoard);
        blueGhost = new Ghost(11,13,3, pacman, pmBoard);
        yellowGhost = new Ghost(14,13,4, pacman, pmBoard);
        pinkGhost = new Ghost(13,15,5, pacman, pmBoard);

        Ghosts = new ArrayList<>();
        Ghosts.add(redGhost);


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
                else if (Board[i][j]==3){
                    imageView.setImageResource(R.drawable.blueghost);
                }
                else if (Board[i][j]==4){
                    imageView.setImageResource(R.drawable.yellowghost);
                }
                else if (Board[i][j]==5){
                    imageView.setImageResource(R.drawable.pinkghost);
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
        f = executor.scheduleAtFixedRate(this::movePacman, 5 * deltaT, deltaT, TimeUnit.MILLISECONDS);
        int DeltaT=250;
        f1 = executor.scheduleAtFixedRate(this::moveGhosts, 5 * DeltaT, DeltaT, TimeUnit.MILLISECONDS);

        moveGhostsWithDelay();
    }
    // Scheduled executors that make use of movePacman() and moveGhosts(). Also makes use
    // of handler that starts moving ghosts with delay
    public void moveGhostsWithDelay(){
        // Adds the other ghosts to Ghosts list every 10 seconds
            AtomicInteger j = new AtomicInteger();
            if (j.get() == 0)
            {
                new Handler().postDelayed(() ->{
                    Ghosts.add(blueGhost);
                    new Handler().postDelayed(() ->{
                        Ghosts.add(yellowGhost);
                        new Handler().postDelayed(() ->{
                            Ghosts.add(pinkGhost);
                        }, 15000);
                    }, 15000);
                }, 15000);
            }
    }

    // Polls the buttons to check if they're pressed. If so, we
    @SuppressLint("SetTextI18n")
    public void movePacman(){
        scoreText.setText("Score " + pacman.getScore());

        // Retrieve old position of PacMan
        int oldXPosition = pacman.getxPosition();
        int oldYPosition = pacman.getyPosition();

        // Move PacMan based on button held
        if (rightButton.isPressed()){
            ImageView imageView1 = (ImageView) gridLayout.getChildAt((oldYPosition *Board[0].length) + oldXPosition);
            runOnUiThread(() ->imageView1.setImageResource(R.drawable.blackbackground));
            pacman.updatePosition(pacman.getxPosition() + 1 , pacman.getyPosition());
            ImageView imageView2 = (ImageView) gridLayout.getChildAt((pacman.getyPosition() *Board[0].length) + pacman.getxPosition());
            runOnUiThread(() ->imageView2.setImageResource(R.drawable.pmopenmouthright));

        }
        if (leftButton.isPressed()){
            ImageView imageView1 = (ImageView) gridLayout.getChildAt((oldYPosition *Board[0].length) + oldXPosition);
            runOnUiThread(() ->imageView1.setImageResource(R.drawable.blackbackground));
            pacman.updatePosition(pacman.getxPosition() - 1, pacman.getyPosition());
            ImageView imageView2 = (ImageView) gridLayout.getChildAt((pacman.getyPosition() *Board[0].length) + pacman.getxPosition());
            runOnUiThread(() ->imageView2.setImageResource(R.drawable.pmopenmouthleft));
        }

        if (upButton.isPressed()){
            ImageView imageView1 = (ImageView) gridLayout.getChildAt((oldYPosition *Board[0].length) + oldXPosition);
            runOnUiThread(() ->imageView1.setImageResource(R.drawable.blackbackground));
            pacman.updatePosition(pacman.getxPosition() , pacman.getyPosition() - 1);
            ImageView imageView2 = (ImageView) gridLayout.getChildAt((pacman.getyPosition() *Board[0].length) + pacman.getxPosition());
            runOnUiThread(() ->imageView2.setImageResource(R.drawable.pmopenmouthup));

        }

        if (downButton.isPressed()){
            ImageView imageView1 = (ImageView) gridLayout.getChildAt((oldYPosition *Board[0].length) + oldXPosition);
            runOnUiThread(() ->imageView1.setImageResource(R.drawable.blackbackground));
            pacman.updatePosition(pacman.getxPosition() , pacman.getyPosition() + 1);
            ImageView imageView2 = (ImageView) gridLayout.getChildAt((pacman.getyPosition() * Board[0].length) + pacman.getxPosition());
            runOnUiThread(() ->imageView2.setImageResource(R.drawable.pmopenmouthdown));
        }

        // Constantly check PacMan's HP to update the hearts at the bottom
        updateHP();
    }


    public void moveGhosts(){
        // First checks if any Ghost collides with pacman. If not, we move them
        for (Ghost X:Ghosts){
            // We first find the next tile the Ghost will step on
            X.findShortestPath();
            // If collision occurs, respawn every element
            if (X.checkPacmanCollision()){
                for (Ghost Y:Ghosts) {
                    Y.setXYNext(Y.getInitialX(),Y.getInitialY());
                    Y.moveByShortestPath();
                    Y.setTileToBeReplaced(9);
                    updateUIGhost(Y);
                    Y.updateCurrentAndNext();
                }
                respawnPacMan();
                break;
            }
            else{
                // If no collision occurs, move the ghost
                X.moveByShortestPath();
                updateUIGhost(X);
                X.updateCurrentAndNext();
            }
        }
    }

    public void updateUIGhost(Ghost ghostToBeMoved){
        // After moving by 1 tile, we replace the old tile with the tile before the ghost stepped on it
        ImageView currentImage = (ImageView) gridLayout.getChildAt((ghostToBeMoved.getY() *Board[0].length) + ghostToBeMoved.getX());
        if (Board[ghostToBeMoved.getY()][ghostToBeMoved.getX()] == 0)
            runOnUiThread(() ->currentImage.setImageResource(R.drawable.pacmansc));
        else
            runOnUiThread(() ->currentImage.setImageResource(R.drawable.blackbackground));

        // We replace the next tile with the ghost
        ImageView nextImage = (ImageView) gridLayout.getChildAt((ghostToBeMoved.getyNext() *Board[0].length) + ghostToBeMoved.getxNext());
        if (ghostToBeMoved.equals(redGhost))
            runOnUiThread(() ->nextImage.setImageResource(R.drawable.redghost));
        else if (ghostToBeMoved.equals(blueGhost))
            runOnUiThread(() ->nextImage.setImageResource(R.drawable.blueghost));
        else if (ghostToBeMoved.equals(yellowGhost))
            runOnUiThread(() ->nextImage.setImageResource(R.drawable.yellowghost));
        else if (ghostToBeMoved.equals(pinkGhost))
            runOnUiThread(() ->nextImage.setImageResource(R.drawable.pinkghost));
    }


    public void updateHP(){
        // Everytime we lose 1 HP, a heart is removed
        if (pacman.getHP() == 2)
            runOnUiThread( () ->HP3.setImageResource(R.drawable.blackbackground));
        else if(pacman.getHP() == 1 )
            runOnUiThread( () ->HP2.setImageResource(R.drawable.blackbackground));
        else if (pacman.getHP() == 0){
            runOnUiThread( () ->HP1.setImageResource(R.drawable.blackbackground));

            // We stop the game
            f.cancel(true);
            f1.cancel(true);
            // YUSUF POP UP MENUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU
        }
    }

    public void respawnPacMan(){

        int oldXPosition = pacman.getxPosition();
        int oldYPosition = pacman.getyPosition();

        ImageView imageView1 = (ImageView) gridLayout.getChildAt((oldYPosition *Board[0].length) + oldXPosition);
        runOnUiThread(() ->imageView1.setImageResource(R.drawable.blackbackground));
        pacman.respawnPacMan();
        ImageView imageView2 = (ImageView) gridLayout.getChildAt((pacman.getyPosition() * Board[0].length) + pacman.getxPosition());
        runOnUiThread(() ->imageView2.setImageResource(R.drawable.pmopenmouthright));
    }

    @Override
    public void onBackPressed(){
        f1.cancel(true);
        f.cancel(true);
        pmBoard.regenerateBoard();
        super.onBackPressed();
    }

}