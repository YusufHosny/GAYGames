package com.example.gaygames.ui.gamesui.Snake;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gaygames.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import games.general.UserData;

public class SnakeLeaderboardActivity extends AppCompatActivity {
    private Class<? extends AppCompatActivity> gameClass;

    private LinkedHashMap<String, Integer> leaderboard;

    private Set<Map.Entry<String, Integer>> selectedLeaderboard;
    private float textSize;
    private int alignment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        gameClass = SnakeActivity.class;

        UserData.setAddURL(gameClass, "https://studev.groept.be/api/a22pt107/addScoreSnake/");
        UserData.setUpdateURL(gameClass, "https://studev.groept.be/api/a22pt107/getLeaderboardSnake");

        renewLeaderboard();

        TextView styleBase = findViewById(R.id.styleP);

        textSize = styleBase.getTextSize();
        alignment = styleBase.getTextAlignment();

        ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
        s.scheduleAtFixedRate(this::displayLeaderboard, 20, 2000, TimeUnit.MILLISECONDS);

        // default leaderboard
        selectedLeaderboard = leaderboard.entrySet();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void renewLeaderboard() {
        UserData.updateLeaderboard(this, gameClass);
        leaderboard = UserData.getLeaderboard(gameClass).getSortedLeaderboard();

        ArrayList<String> friendsList = UserData.getFriendsList();

        LinkedHashSet<Map.Entry<String, Integer>> friendsLeaderboard = leaderboard.entrySet().stream()
                .filter(e ->
                        friendsList.contains(e.getKey()) || e.getKey().equals(UserData.getUsername()))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        // check if friends or normal leaderboard
        Switch friendLeaderboardSwitch = findViewById(R.id.FriendsToggle);
        if(friendLeaderboardSwitch.isChecked()) {
            selectedLeaderboard = friendsLeaderboard;
        } else {
            selectedLeaderboard = leaderboard.entrySet();
        }
    }

    private void displayLeaderboard() {
        renewLeaderboard();

        runOnUiThread( () -> {
            LinearLayout leaderboardContainer = findViewById(R.id.leaderboardContainer);

            leaderboardContainer.removeAllViews();

            for(Map.Entry<String, Integer> entry : selectedLeaderboard) {
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
