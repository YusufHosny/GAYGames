package com.example.gaygames.ui.gamesui.RockPaperScissors;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gaygames.R;

public class RPSActivity extends AppCompatActivity {

    private TextView playerLabel1,playerLabel2;
    private ImageButton RB1,PB1,SB1,RB2,PB2,SB2;
    private String P1M,P2M;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpsactivity);
        //Initializing buttons and textViews
        RB1 = (ImageButton) findViewById(R.id.rockButton1);
        PB1 = (ImageButton) findViewById(R.id.paperButton1);
        SB1 = (ImageButton) findViewById(R.id.scissorsButton1);
        RB2 = (ImageButton) findViewById(R.id.rockButton2);
        PB2 = (ImageButton) findViewById(R.id.paperButton2);
        SB2 = (ImageButton) findViewById(R.id.scissorsButton2);
        playerLabel1 = (TextView) findViewById(R.id.player1TextView);
        playerLabel2 = (TextView) findViewById(R.id.player2TextView);
    }

    @SuppressLint("SetTextI18n")
    public void player1Move(View Caller){
        ImageButton button = (ImageButton) Caller;
        P1M = button.getTag().toString();
        switchTurns();
        playerLabel1.setText("Player1");
        playerLabel2.setText("Player 2, your turn");
    }

    public void player2Move(View Caller){
        ImageButton button = (ImageButton) Caller;
        P2M = button.getTag().toString();
        RB2.setEnabled(false);
        PB2.setEnabled(false);
        SB2.setEnabled(false);
        new Handler().postDelayed(this::Decision, 1000);
    }


    @SuppressLint("SetTextI18n")
    public void Decision(){
        if (P1M !=null && P2M !=null){
            if (P1M.equals(P2M)){
                playerLabel1.setText(P1M + ",It's a Tie");
                playerLabel2.setText(P2M + ",It's a Tie");
            }
            else if ((P1M.equals("Rock") && P2M.equals("Scissors")) || (P1M.equals("Paper") &&
                    P2M.equals("Rock")) || (P1M.equals("Scissors") && P2M.equals("Paper"))){
                playerLabel1.setText(P1M + ",You win!");
                playerLabel2.setText(P2M + ",You lose!");
            }
            else {
                playerLabel1.setText(P1M + ",You lose!");
                playerLabel2.setText(P2M + ",You win!");
            }
        }
    }

    public void switchTurns(){
        RB1.setEnabled(false);
        PB1.setEnabled(false);
        SB1.setEnabled(false);
        RB2.setEnabled(true);
        PB2.setEnabled(true);
        SB2.setEnabled(true);
    }

}