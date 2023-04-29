package com.example.gaygames.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gaygames.R;
import com.example.gaygames.ui.home.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import games.general.UserData;

public class LoginMenuActivity extends AppCompatActivity {

    private TextView nameTxt, pwdTxt;

    String username, password;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);

        nameTxt = findViewById(R.id.UsernameText);
        pwdTxt = findViewById(R.id.PsdText);
        requestQueue = Volley.newRequestQueue(this);
    }

    private int hashPassword(String password) {
        return password.hashCode();
    }


    public void httpGet(String requestURL, Response.Listener<String> r, Response.ErrorListener e) {
        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL, r, e);
        requestQueue.add(submitRequest);
    }

    public void login(View Caller){
        retrieveCredentials();

        httpGet("https://studev.groept.be/api/a22pt107/LogIn/" + hashPassword(password)
                        + "/" + username,
                response -> {
                    try {
                        JSONArray responseArray = new JSONArray(response);
                        JSONObject curObject = responseArray.getJSONObject( 0 );
                        if(curObject.getInt("correct") == 1) {
                            UserData.initialize(curObject.getInt("AccountID"), this);
                            goHome();
                        }  else {
                            Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch( JSONException e )
                    {
                        Toast.makeText(getApplicationContext(), "Username does not exist", Toast.LENGTH_SHORT).show();
                        Log.e( "LoginDB", e.getMessage(), e );
                    }
                },
                error -> Log.e( "LoginDB", error.getLocalizedMessage(), error )
        );



    }
    public void CreateAccount(View Caller){
        retrieveCredentials();

        httpGet("https://studev.groept.be/api/a22pt107/addAccount/" + username + "/" +
                        hashPassword(password) + "/" + username,
                response -> Toast.makeText(getApplicationContext(), "Account Creation Successful", Toast.LENGTH_SHORT).show(),
                error -> {
                    Log.e( "LoginDB", error.getLocalizedMessage(), error );
                    Toast.makeText(getApplicationContext(), "Account Creation Failed: User Already Exists", Toast.LENGTH_SHORT).show();
                }
        );

    }

    public void retrieveCredentials(){
        username = nameTxt.getText().toString();
        password = pwdTxt.getText().toString();
    }
    public void goHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}