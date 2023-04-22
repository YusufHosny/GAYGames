package com.example.gaygames.ui.gamesui.runner;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gaygames.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import games.runner.Obstacle;
import games.runner.ObstacleFactory;
import games.runner.Runner;

public class RunnerActivity extends AppCompatActivity {
    // gravity in pixels/second
    public static final int gravity = 150;

    // speed increase per frame
    public static final int speedIncrease = 2;

    // time between each frame in milliseconds
    public static final int deltaT = 40;
    private int scrollSpeed;


    private Runner runner;

    private boolean gameDone;

    // Executor future for the frames, to be canceled when game is done
    ScheduledFuture<?> f;
    ObstacleFactory obstacleFactory;

    private Obstacle obs;

    private int score;

    private TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner);


        // set scoreView to correct id
        scoreView = findViewById(R.id.scoreView);

        // wait for the layout to finish inflating to initialize
        View rootView = findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initialize();

                // Remove the listener to avoid memory leaks
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        f = executor.scheduleAtFixedRate(() -> {
                nextFrame();
                if(gameDone) {
                    f.cancel(true);
                }
            }, 5 * deltaT, deltaT, TimeUnit.MILLISECONDS);

        // make the runner jump when screen is clicked
        findViewById(R.id.RunnerBg).setOnClickListener(v -> runner.jump());

    }

    // initialize the game
    public void initialize() {
        // set default values
        runner = new Runner(findViewById(R.id.runnerImg));
        gameDone = false;
        scrollSpeed = 30;
        resetScore();
        findViewById(R.id.RunnerBg).setClickable(true);

        // create obstacles
        obstacleFactory = new ObstacleFactory(this);
        obs = obstacleFactory.createObstactle();


    }

    public void nextFrame() {
        scrollSpeed += speedIncrease;
        runner.next();
        obs.next(scrollSpeed);
        // if theres a collision end the game
        if(obs.checkCollision(runner)) {
            setGameDone(true);
        }

    }


    public void setGameDone(boolean g) {
        gameDone = g;
    }


    public void resetScore() {
        score = 0;
        refreshScoreView();
    }

    public void incrementScore() {
        score += 1;
        refreshScoreView();
    }

    public void refreshScoreView() {
        runOnUiThread(() -> scoreView.setText(String.valueOf(score)));
    }
}