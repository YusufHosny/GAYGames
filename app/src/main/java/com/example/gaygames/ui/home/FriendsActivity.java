package com.example.gaygames.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gaygames.R;

import java.util.ArrayList;

import games.general.UserData;

public class FriendsActivity extends AppCompatActivity {

    private ArrayList<String> friendsList;

    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        // initialize buttons
        ImageButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(v ->
                addFriend(String.valueOf(((EditText) findViewById(R.id.friendInput)).getText())));
        ImageButton refreshBtn = findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(v -> updateFriendsList());

        requestQueue = Volley.newRequestQueue(this);
    }

    public void updateFriendsList() {
        UserData.updateFriendsList(this);
        friendsList = UserData.getFriendsList();

        displayFriendsList();
    }


    public void displayFriendsList() {
        // remove all views
        ((LinearLayout) findViewById(R.id.friendsPane)).removeAllViews();

        // for each friend
        for(String friend: friendsList) {
            // create friend entry visuals
            // entry container (horizontal layout)
            LinearLayout friendContainer = new LinearLayout(this);
            friendContainer.setOrientation(LinearLayout.HORIZONTAL);
            // get friend name
            TextView friendName = new TextView(this);
            friendName.setText(friend);

            // friend status
            ImageView friendStatus = new ImageView(this);
            Drawable onlineicon;

            if(UserData.isOnline(friend)) onlineicon = AppCompatResources
                        .getDrawable(this, android.R.drawable.radiobutton_on_background);
            else onlineicon = AppCompatResources
                    .getDrawable(this, android.R.drawable.radiobutton_off_background);

            friendStatus.setImageDrawable(onlineicon);

            // add all to layout then add to main layout
            friendContainer.addView(friendName);
            friendContainer.addView(friendStatus);
            ((LinearLayout) findViewById(R.id.friendsPane)).addView(friendContainer);
        }
    }


    public void addFriend(String friendName) {
        // add a friend then update the friendslist
        StringRequest addFriend = new StringRequest(
                "https://studev.groept.be/api/a22pt107/addFriend/" + friendName + "/" + UserData.accountID,
                response -> updateFriendsList(),
                error -> Log.e("addFriend", error.getLocalizedMessage(), error)
        );

        requestQueue.add(addFriend);
    }


}