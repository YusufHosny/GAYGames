package com.example.gaygames.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gaygames.R;
import com.example.gaygames.ui.home.HomeActivity;

public class LoginMenu extends AppCompatActivity {

    private TextView Username,Password;
    private Button Login,CreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_menu);

        Username = (TextView) findViewById(R.id.UsernameText);
        Password = (TextView) findViewById(R.id.PsdText);
        Login = (Button)findViewById(R.id.LButton);
        CreateAccount = (Button)findViewById(R.id.CCButton);
    }

    public  void login(View Caller){
        retrieveCredentials();
        //code

        goHome();
    }
    public void CreateAccount(View Caller){
        retrieveCredentials();
        //code
        goHome();
    }

    public void retrieveCredentials(){
        String UN = Username.getText().toString();
        String PSD = Password.getText().toString();
    }
    public void goHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}