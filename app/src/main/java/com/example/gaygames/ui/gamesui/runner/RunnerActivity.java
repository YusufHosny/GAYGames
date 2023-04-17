package com.example.gaygames.ui.gamesui.runner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gaygames.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import games.runner.Obstacles;
import games.runner.Runner;

public class RunnerActivity extends AppCompatActivity {
    // speeds are all given as pixels/second
    public static final int gravity = 20;
    public static final int speedIncrease = 2;

    // time between each frame in milliseconds
    public static final int deltaT = 60;
    private int scrollSpeed;


    private Runner runner;

    private boolean gameDone;

    // Executor future for the frames, to be canceled when game is done
    ScheduledFuture<?> f;
    Obstacles obstacles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner);

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
        scrollSpeed = 10;
        findViewById(R.id.RunnerBg).setClickable(true);

        // create obstacles
        obstacles = new Obstacles(this);
        obstacles.addObstacle();

    }

    public void nextFrame() {
        scrollSpeed += speedIncrease;
        runner.next();
        obstacles.next();
        // if theres a collision end the game
        if(obstacles.checkCollisions(runner)) {
            setGameDone(true);
            Log.d("ydebug", "gameDone");
        }

    }

    public int getScrollSpeed() {
        return scrollSpeed;
    }

    public void setGameDone(boolean g) {
        gameDone = g;
    }
}