package com.example.gaygames.ui.gamesui.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gaygames.R;

public class TicTacToeMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe_menu);
    }

    public void singplayerOnClick(View v) {
        Intent intent = new Intent(this, TicTacToe1PActivity.class);
        startActivity(intent);
    }

    public void multiplayerOnClick(View v) {
        Intent intent = new Intent(this, TicTacToeMPActivity.class);
        startActivity(intent);
    }

}