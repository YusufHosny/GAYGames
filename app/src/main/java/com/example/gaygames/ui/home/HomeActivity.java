package com.example.gaygames.ui.home;

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

        // Create a GameButton for the placeholder game
        GameButton firstGameButton = new GameButton((ImageButton) findViewById(R.id.placeholderBtn), new PlaceholderGame());

        GameButton tttGameButton = new GameButton((ImageButton) findViewById(R.id.TTTBtn), new TicTacToeGame());

        GameButton runnerGameButton = new GameButton((ImageButton) findViewById(R.id.runnerBtn), new RunnerGame());

        GameButton RPSGameButton = new GameButton((ImageButton) findViewById(R.id.RPSBtn), new RPSGame());
        GameButton SnakeGameButton = new GameButton((ImageButton) findViewById(R.id.snakeBtn), new SnakeGame());

    }


}