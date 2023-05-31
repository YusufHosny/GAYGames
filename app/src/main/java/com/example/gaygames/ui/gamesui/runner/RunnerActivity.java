package com.example.gaygames.ui.gamesui.runner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gaygames.R;
import com.example.gaygames.ui.gamesui.general.gamePopUpMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import games.general.Animatable;
import games.general.Animator;
import games.general.UserData;
import games.runner.Obstacle;
import games.runner.ObstacleFactory;
import games.runner.ParallaxBackground;
import games.runner.Runner;

public class RunnerActivity extends AppCompatActivity implements Animatable {
    // gravity in pixels/second
    public static final int gravity = 150;

    // speed increase per frame
    public static final int speedIncrease = 2;

    // time between each frame in milliseconds
    public static final int deltaT = 40;
    public static int scrollSpeed;


    private Runner runner;

    private boolean gameDone;

    ObstacleFactory obstacleFactory;

    private Obstacle obs;

    private int score;

    private TextView scoreView;

    ParallaxBackground bg;

    Animator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner);

        // create animator
        animator = new Animator(deltaT);

        // set scoreView to correct id
        scoreView = findViewById(R.id.scoreView);

        // wait for the layout to finish inflating to initialize
        View rootView = findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                start();

                // Remove the listener to avoid memory leaks
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        // make the runner jump when screen is clicked
        findViewById(R.id.RunnerBg).setOnClickListener(v -> runner.jump());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        animator.terminate();
        this.finish();
    }

    // initialize the game
    public void start() {
        // set default values
        runner = new Runner(findViewById(R.id.runnerImg));
        gameDone = false;
        scrollSpeed = 30;
        resetScore();
        findViewById(R.id.RunnerBg).setClickable(true);

        // create obstacles
        obstacleFactory = new ObstacleFactory(this);
        obs = obstacleFactory.createObstactle();

        // create parallax bg
        bg = new ParallaxBackground(findViewById(R.id.parallaxA1), findViewById(R.id.parallaxA2),
                findViewById(R.id.parallaxB1), findViewById(R.id.parallaxB2),
                findViewById(R.id.parallaxC1), findViewById(R.id.parallaxC2), 160);


        // add animatables to the animator
        animator.add(runner);
        animator.add(obs);
        animator.add(bg);
        animator.add(this);

        // begin animation
        animator.animate();
    }

    public void repeat() {
        // increment scroll speed
        scrollSpeed += speedIncrease;

        // if theres a collision end the game
        if(obs.checkCollision(runner)) {
            setGameDone(true);
        }

        // end the animation if game is done
        if(gameDone) {
            animator.terminate();
        }

    }


    public void setGameDone(boolean g) {
        gameDone = g;

        if(g) {
            // check if highscore and if it is update leaderboard
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest sendHighscore = new StringRequest( Request.Method.GET,
                    "https://studev.groept.be/api/a22pt107/addScoreRunner/" + UserData.accountID + "/" + score,
                    response -> {},
                    error -> Log.e("sendHighscoreRunner", error.getLocalizedMessage(), error));


            StringRequest getHighscore = new StringRequest( Request.Method.GET,
                    "https://studev.groept.be/api/a22pt107/getHighestPersonalScoreRunner/" + UserData.accountID,
                    response -> {
                        try {
                            JSONArray responseArray = new JSONArray(response);
                            JSONObject object = responseArray.getJSONObject(0);

                            int prevHighscore = object.getInt("score");

                            if(prevHighscore < score) {
                                requestQueue.add(sendHighscore);
                            }

                        } catch(JSONException e) {
                            Log.e( "runnerGetScore", e.getMessage(), e );
                            requestQueue.add(sendHighscore);
                        }
                    },
                    error -> Log.e( "runnerGetScore", error.getLocalizedMessage(), error )
            );

            requestQueue.add(getHighscore);

            // open end game pop up
            gamePopUpMenu.setLeaderboardActivity(RunnerLeaderboardActivity.class);
            getSupportFragmentManager().beginTransaction().replace(R.id.RunnerBg, new gamePopUpMenu()).commit();

        }
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