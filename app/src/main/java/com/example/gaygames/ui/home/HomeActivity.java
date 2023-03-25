package com.example.gaygames.ui.home;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gaygames.R;

import games.general.GameButton;
import games.placeholder.PlaceholderGame;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GameButton firstGameButton = new GameButton((Button) findViewById(R.id.button1), new PlaceholderGame());

    }


}