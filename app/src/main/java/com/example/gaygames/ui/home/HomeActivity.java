package com.example.gaygames.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gaygames.R;

import games.RockPaperScissors.RPSGame;
import games.Snake.SnakeGame;
import games.TicTacToe.TicTacToeGame;
import games.general.GameButton;
import games.placeholder.PlaceholderGame;
import games.runner.RunnerGame;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Create a GameButton for each game
        new GameButton((ImageButton) findViewById(R.id.placeholderBtn), new PlaceholderGame());

        new GameButton((ImageButton) findViewById(R.id.TTTBtn), new TicTacToeGame());

        new GameButton((ImageButton) findViewById(R.id.runnerBtn), new RunnerGame());

        new GameButton((ImageButton) findViewById(R.id.RPSBtn), new RPSGame());

        new GameButton((ImageButton) findViewById(R.id.snakeBtn), new SnakeGame());

        findViewById(R.id.friendsBtn).setOnClickListener(view -> {
            Intent intent = new Intent(this, FriendsActivity.class);
            startActivity(intent);
        });

    }


}