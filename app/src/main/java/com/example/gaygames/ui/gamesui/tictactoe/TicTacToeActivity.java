package com.example.gaygames.ui.gamesui.tictactoe;

import static games.TicTacToe.TicTacToeTile.getChoiceString;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gaygames.R;

import java.util.ArrayList;

import games.TicTacToe.TicTacToeChoices;
import games.TicTacToe.TicTacToeTile;

public class TicTacToeActivity extends AppCompatActivity {

    // current turn the game is on (turn counter)
    private int turn;
    private ArrayList<TicTacToeTile> board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        // initialize variables
        turn = 0;
        board = new ArrayList<>();

        // define board and tiles using the buttons in the button container
        board.add(new TicTacToeTile((Button) findViewById(R.id.TTTb1)));
        board.add(new TicTacToeTile((Button) findViewById(R.id.TTTb2)));
        board.add(new TicTacToeTile((Button) findViewById(R.id.TTTb3)));
        board.add(new TicTacToeTile((Button) findViewById(R.id.TTTb4)));
        board.add(new TicTacToeTile((Button) findViewById(R.id.TTTb5)));
        board.add(new TicTacToeTile((Button) findViewById(R.id.TTTb6)));
        board.add(new TicTacToeTile((Button) findViewById(R.id.TTTb7)));
        board.add(new TicTacToeTile((Button) findViewById(R.id.TTTb8)));
        board.add(new TicTacToeTile((Button) findViewById(R.id.TTTb9)));




    }


    // return all win con checks
    public boolean checkWin() {
        return checkVerticals() || checkHorizontals() || checkDiagonals();
    }


    // check vertical win con
    public boolean checkVerticals() {
        for(int i = 0; i < 3; i++) {
            if(board.get(i).getChoice() == board.get(i+3).getChoice()
                    && board.get(i+3).getChoice() == board.get(i+6).getChoice()
                    && board.get(i).getChoice() != TicTacToeChoices.BLANK) {
                return true;
            }
        }
        return false;
    }

    // check horizontal win con
    public boolean checkHorizontals() {
        for(int i = 0; i < 3; i++) {
            int j = i*3;
            if(board.get(j).getChoice() == board.get(j+1).getChoice()
                    && board.get(j+1).getChoice() == board.get(j+2).getChoice()
                    && board.get(j).getChoice() != TicTacToeChoices.BLANK) {
                return true;
            }
        }
        return false;
    }

    // check diagonal win con
    public boolean checkDiagonals() {
        // if the middle tile is empty then no diagonal match
        if(board.get(4).getChoice() == TicTacToeChoices.BLANK) {
            return false;
        }

        if(board.get(0).getChoice() == board.get(4).getChoice()
                && board.get(4).getChoice() == board.get(8).getChoice()) {
            return true;
        } else return board.get(2).getChoice() == board.get(4).getChoice()
                && board.get(4).getChoice() == board.get(6).getChoice();
    }

    // method for tictactoe button onclick
    public void tttClicked(View v) {
        // increment turn
        turn += 1;

        // odd turns are X even turns are O
        TicTacToeChoices choice = (turn % 2 == 0) ? TicTacToeChoices.O : TicTacToeChoices.X;

        // get tile from button
        TicTacToeTile tile = findTileByButton((Button) v);

        tile.playTile(choice);

        // if the game is done
        if(checkWin()) {
            //  update bottom text
            TextView btmTxt = findViewById(R.id.TTTbtmTxt);
            btmTxt.setText(getString(R.string.btmTxtWon, getChoiceString(choice)));

            // set all buttons to unclickable
            for(TicTacToeTile t: board) {
                t.getButton().setClickable(false);
            }
        }
    }


    // find a tile by its button
    public TicTacToeTile findTileByButton(Button b) {
        for(TicTacToeTile tile: board) {
            if(tile.getButton() == b) {
                return tile;
            }
        }
        return null;
    }
}