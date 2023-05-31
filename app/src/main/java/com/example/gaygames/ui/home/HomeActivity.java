package com.example.gaygames.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gaygames.R;

import games.PacMan.PacManGame;
import games.RockPaperScissors.RPSGame;
import games.Snake.SnakeGame;
import games.TicTacToe.TicTacToeGame;
import games.general.GameButton;
import games.general.UserData;
import games.runner.RunnerGame;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setOnlineStatus(true);

        // Create a GameButton for each game
        new GameButton((ImageButton) findViewById(R.id.TTTBtn), new TicTacToeGame());

        new GameButton((ImageButton) findViewById(R.id.runnerBtn), new RunnerGame());

        new GameButton((ImageButton) findViewById(R.id.RPSBtn), new RPSGame());

        new GameButton((ImageButton) findViewById(R.id.snakeBtn), new SnakeGame());
      
        new GameButton((ImageButton) findViewById(R.id.pacmanBtn), new PacManGame());

        findViewById(R.id.friendsBtn).setOnClickListener(view -> {
            Intent intent = new Intent(this, FriendsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        setOnlineStatus(false);

    }

    private void setOnlineStatus(boolean status) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest onlineStatusSet = new StringRequest(
                "https://studev.groept.be/api/a22pt107/setOnlineStatus/" + (status ? "1" : "0")
                        + "/" + UserData.getUsername(),
                response -> {},
                error -> Log.e("setonline", error.getLocalizedMessage(), error)
        );

        requestQueue.add(onlineStatusSet);

    }
}