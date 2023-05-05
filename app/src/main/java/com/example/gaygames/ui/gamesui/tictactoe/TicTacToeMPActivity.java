package com.example.gaygames.ui.gamesui.tictactoe;

import static games.TicTacToe.TicTacToeTile.getChoiceString;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gaygames.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import games.TicTacToe.TicTacToeChoices;
import games.TicTacToe.TicTacToeMPData;
import games.TicTacToe.TicTacToeTile;

public class TicTacToeMPActivity extends AppCompatActivity {

    // current turn the game is on (turn counter)
    private ArrayList<TicTacToeTile> board;

    private ArrayList<Integer> moves;

    RequestQueue requestQueue;
    private TicTacToeChoices choice;

    private int matchId;

    ScheduledFuture<?> f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        // create request queue
        requestQueue = Volley.newRequestQueue(this);

        // initialize variables
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

        // set correct onclicklistener
        for(TicTacToeTile t: board) {
            t.getButton().setOnClickListener(this::tttMPClicked);
        }

        // get the choice for this player
        getChoice();

        // get the match Id
        matchId = TicTacToeMPData.getCurrentMatchId();

        // set the code text
        ((TextView) findViewById(R.id.TTTtopTxt)).setText(TicTacToeMPData.getCode(matchId));

        // schedule the updates
        ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
        f = s.scheduleAtFixedRate(this::tttUpdate, 2, 2, TimeUnit.SECONDS);

    }


    public void setClickable() {
        for(TicTacToeTile t: board) {
            t.getButton().setClickable(true);
        }
    }

    public void setUnclickable() {
        for(TicTacToeTile t: board) {
            t.getButton().setClickable(false);
        }
    }

    public void tttUpdate() {
        // request latest moves
        StringRequest submitRequest = new StringRequest(Request.Method.GET,
                "https://studev.groept.be/api/a22pt107/getMovesXO/" + matchId,
                response -> {
                    try {
                        JSONArray responseArray = new JSONArray(response);
                        JSONObject object = responseArray.getJSONObject(0);
                        // parse the received move string
                        parseMoves(object.getString("moves"), object.getInt("turn"));
                    } catch( JSONException e ) {
                        Log.e( "tttSetChoice", e.getMessage(), e );
                    }
                },
                error -> Log.e( "tttSetChoice", error.getLocalizedMessage(), error )
        );
        requestQueue.add(submitRequest);

    }

    // parse moves string and update screen
    public void parseMoves(String moveString, int turn) {
        // reset move list
        moves = new ArrayList<>();
        String parsedMoves;
        try { parsedMoves = moveString.substring(2);}
        catch (Exception e) {return;}

        // add moves to move list
        for(int i = 0; i < parsedMoves.length(); i++) {
            if(parsedMoves.charAt(i) != ',') {
                moves.add(Character.getNumericValue(parsedMoves.charAt(i)));
            }
        }

        // update board
        updateBoard(turn);

        // set unclickable if not your turn vice versa
        if(choice == TicTacToeChoices.X) {
            if(turn == 1) setClickable();
            else setUnclickable();
        } else {
            if(turn == 2) setClickable();
            else setUnclickable();
        }
    }

    public void updateBoard(int turn) {
        for(int i = 0; i < moves.size(); i++) {
            if(i%2 == 0) board.get(moves.get(i)-1).playTile(TicTacToeChoices.X);
            else board.get(moves.get(i)-1).playTile(TicTacToeChoices.O);
        }

        // if the game is done
        if(checkWin()) {
            //  update bottom text
            TextView btmTxt = findViewById(R.id.TTTbtmTxt);
            TicTacToeChoices c = turn == 1 ? TicTacToeChoices.X : TicTacToeChoices.O;
            btmTxt.setText(getString(R.string.btmTxtWon, getChoiceString(c)));
            endGame();

            // set all buttons to unclickable
            for(TicTacToeTile t: board) {
                t.getButton().setClickable(false);
            }
        }
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


    // find out if player should be X or O
    public void getChoice() {
        choice = TicTacToeMPData.choice;
    }

    // method for tictactoe button onclick
    public void tttMPClicked(View v) {
        // get tile from button
        TicTacToeTile tile = findTileByButton((Button) v);

        tile.playTile(choice);
        setUnclickable();

        playChoiceToDB(choice, findTileIndexByButton((Button) v));


        // if the game is done
        if(checkWin()) {
            //  update bottom text
            TextView btmTxt = findViewById(R.id.TTTbtmTxt);
            btmTxt.setText(getString(R.string.btmTxtWon, getChoiceString(choice)));
            endGame();

            // set all buttons to unclickable
            for(TicTacToeTile t: board) {
                t.getButton().setClickable(false);
            }
        }

        // set to onclick listener that doesn nothing after its clicked
        tile.getButton().setOnClickListener(ve -> {});
    }


    // send the played choice to the DB
    public void playChoiceToDB(TicTacToeChoices c, int pos) {
        if(c == TicTacToeChoices.X) {
            StringRequest submitRequest = new StringRequest(Request.Method.GET,
                    "https://studev.groept.be/api/a22pt107/playX/" + pos + "/" + matchId,
                    response -> {},
                    error -> Log.e( "tttcreate", error.getLocalizedMessage(), error )
            );
            requestQueue.add(submitRequest);
            Log.d("ydebug", "https://studev.groept.be/api/a22pt107/playX/" + pos + "/" + matchId);
        } else {
            StringRequest submitRequest = new StringRequest(Request.Method.GET,
                    "https://studev.groept.be/api/a22pt107/playO/" + pos + "/" + matchId,
                    response -> {},
                    error -> Log.e( "tttcreate", error.getLocalizedMessage(), error )
            );
            requestQueue.add(submitRequest);
        }
    }

    public void endGame() {
        f.cancel(false);
        StringRequest submitRequest = new StringRequest(Request.Method.GET,
                "https://studev.groept.be/api/a22pt107/endMatch/" + matchId,
                response -> {},
                error -> Log.e( "tttSetChoice", error.getLocalizedMessage(), error )
        );
        requestQueue.add(submitRequest);
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

    // find a tile index by its button
    public int findTileIndexByButton(Button b) {
        int count = 0;
        for(TicTacToeTile tile: board) {
            count+=1;
            if(tile.getButton() == b) {
                return count;
            }
        }
        return -1;
    }
}
