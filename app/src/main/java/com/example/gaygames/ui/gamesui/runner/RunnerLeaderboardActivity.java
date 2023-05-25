package com.example.gaygames.ui.gamesui.runner;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gaygames.R;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import games.general.UserData;

public class RunnerLeaderboardActivity extends AppCompatActivity {

    private Class<? extends AppCompatActivity> gameClass;

    private LinkedHashMap<String, Integer> leaderboard;
    private float textSize;
    private int alignment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        gameClass = RunnerActivity.class;

        UserData.setAddURL(gameClass, "https://studev.groept.be/api/a22pt107/addScoreRunner/");
        UserData.setUpdateURL(gameClass, "https://studev.groept.be/api/a22pt107/getLeaderboardRunner");

        renewLeaderboard();

        TextView styleBase = findViewById(R.id.styleP);

        textSize = styleBase.getTextSize();
        alignment = styleBase.getTextAlignment();

        ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
        s.scheduleAtFixedRate(this::displayLeaderboard, 20, 2000, TimeUnit.MILLISECONDS);
        
        

    }


    private void renewLeaderboard() {
        UserData.updateLeaderboard(this, gameClass);
        leaderboard = UserData.getLeaderboard(gameClass).getSortedLeaderboard();
    }

    private void displayLeaderboard() {
        renewLeaderboard();

        runOnUiThread( () -> {
            LinearLayout leaderboardContainer = findViewById(R.id.leaderboardContainer);

            leaderboardContainer.removeAllViews();

            for(Map.Entry<String, Integer> entry : leaderboard.entrySet()) {
                TextView t = new TextView(this);
                t.setTextSize(textSize);
                t.setTextAlignment(alignment);
                String scoreString = entry.getKey() + "   " + entry.getValue();

                t.setText(scoreString);
                leaderboardContainer.addView(t);
            }
        });
    }
}