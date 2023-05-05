package com.example.gaygames.ui.gamesui.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import games.TicTacToe.TicTacToeChoices;
import games.TicTacToe.TicTacToeMPData;
import games.general.UserData;

public class TicTacToeMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe_menu);
    }

    public void singlePlayerOnClick(View v) {
        Intent intent = new Intent(this, TicTacToe1PActivity.class);
        startActivity(intent);
    }

    public void multiplayerOnClick(View v) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest submitRequest2 = new StringRequest(Request.Method.GET,
            "https://studev.groept.be/api/a22pt107/getMatchIdForPlayer/" + UserData.accountID + "/" + UserData.accountID,
                    response -> {
                        try {
                            JSONArray responseArray = new JSONArray(response);
                            JSONObject object = responseArray.getJSONObject(0);
                            TicTacToeMPData.setCurrentMatchId( object.getInt("matchId") );
                            TicTacToeMPData.choice = TicTacToeChoices.X;

                            Intent intent = new Intent(this, TicTacToeMPActivity.class);
                            startActivity(intent);

                        } catch( JSONException e ) {
                            Log.e( "tttcreate", e.getMessage(), e );
                        }
                    },
                error -> Log.e( "tttcreate", error.getLocalizedMessage(), error ));


        StringRequest submitRequest = new StringRequest(Request.Method.GET,
                "https://studev.groept.be/api/a22pt107/createMatch/" + UserData.accountID,
                response -> requestQueue.add(submitRequest2),
                error -> Log.e( "tttcreate", error.getLocalizedMessage(), error )
        );
        requestQueue.add(submitRequest);
    }


    public void joinMPonClick(View v) {
        String txt = String.valueOf(((TextView) findViewById(R.id.gameCodeTxt)).getText());
        joinMPGame(txt);
    }



    // join match by code button
    public void joinMPGame(String code) {
        // get match id from code
        int id = TicTacToeMPData.getMatchIdByCode(code);

        Log.d("ydebug", String.valueOf(id));

        // join match request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest submitRequest = new StringRequest(Request.Method.GET,
                "https://studev.groept.be/api/a22pt107/joinMatch/" + id + "/" + id,
                response -> {
                    TicTacToeMPData.choice = TicTacToeChoices.O;
                    TicTacToeMPData.setCurrentMatchId(id);

                    Intent intent = new Intent(this, TicTacToeMPActivity.class);
                    startActivity(intent);
                },
                error -> Log.e( "tttcreate", error.getLocalizedMessage(), error )
        );
        requestQueue.add(submitRequest);
    }

}