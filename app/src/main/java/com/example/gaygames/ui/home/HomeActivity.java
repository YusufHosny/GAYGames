package com.example.gaygames.ui.home;

import android.os.Bundle;
import android.widget.Button;

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
        GameButton firstGameButton = new GameButton((Button) findViewById(R.id.button1), new PlaceholderGame());

        GameButton tttGameButton = new GameButton((Button) findViewById(R.id.button2), new TicTacToeGame());

        GameButton runnerGameButton = new GameButton((Button) findViewById(R.id.button3), new RunnerGame());

        GameButton RPSGameButton = new GameButton((Button) findViewById(R.id.button4), new RPSGame());
        //GameButton SnakeGameButton = new GameButton((Button) findViewById(R.id.button5), new SnakeGame());

    }


}