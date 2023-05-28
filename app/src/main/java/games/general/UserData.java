package games.general;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import games.TicTacToe.TicTacToeMPData;

public class UserData {

    // hashmap of all the leaderboards and their associated activities
    private static HashMap<Class<? extends AppCompatActivity>, Leaderboard> leaderboardHashMap;

    // account ID for the database for the current log in session
    public static int accountID;

    // friends id list
    private static ArrayList<Integer> friendsList;

    // method to get friends list
    public static ArrayList<Integer> getFriendsList(){
        return friendsList;
    }


    // intialize the userdata
    public static void initialize(int ID, AppCompatActivity a) {
        accountID = ID;
        updateFriendsList(a);
        TicTacToeMPData.genCodeList();

        leaderboardHashMap = new HashMap<>();
    }

    // update the friendslist
    public static void updateFriendsList(AppCompatActivity a) {
        RequestQueue requestQueue = Volley.newRequestQueue(a);
        StringRequest submitRequest = new StringRequest(Request.Method.GET,
                "https://studev.groept.be/api/a22pt107/getFriendString/" + accountID,
                response -> {
                    try {
                        JSONArray responseArray = new JSONArray(response);
                        JSONObject object = responseArray.getJSONObject(0);
                        String friendString = object.getString("friendList");
                        // get the friends list then parse it here
                        parseFriendString(friendString);
                    } catch( JSONException e ) {
                        Log.e( "LeaderboardDB", e.getMessage(), e );
                    }
                },
                error -> Log.e( "LeaderboardDB", error.getLocalizedMessage(), error )
        );
        requestQueue.add(submitRequest);
    }

    // parse the friendslist into friend ids
    public static void parseFriendString(String friendString) {
        friendsList = new ArrayList<>();
        // if friendstring is empty, do nothing
        if(friendString.equals(" ")) return;

        // new empty stringBuilder to get friends from
        StringBuilder collectorString = new StringBuilder();
        friendString = friendString.substring(2);
        // iterate over friendString
        for(int i = 0; i <friendString.length(); i++) {
            // if , add the friend and reset the collectorString
            if(friendString.charAt(i) == ',') {
                int friend = Integer.parseInt(collectorString.toString());
                friendsList.add(friend);
                collectorString = new StringBuilder();
            } else {
                collectorString.append(friendString.charAt(i));
            }
        }
    }

    public static void addLeaderboard(Class<? extends AppCompatActivity> a) {
        leaderboardHashMap.put(a, new Leaderboard());
    }

    public static Leaderboard getLeaderboard(Class<? extends AppCompatActivity> a) {
        if(leaderboardHashMap.get(a) == null) {
            addLeaderboard(a);
            return getLeaderboard(a);
        }
        return leaderboardHashMap.get(a);
    }

    public static void updateLeaderboard(AppCompatActivity a, Class<? extends AppCompatActivity> c) {
        Objects.requireNonNull(leaderboardHashMap.get(c)).update(a);
    }

    public static void setUpdateURL(Class<? extends AppCompatActivity> a, String url) {
        getLeaderboard(a).setRequestURL(url);
    }
    public static void setAddURL(Class<? extends AppCompatActivity> a, String url) {
        getLeaderboard(a).setAddScoreURL(url);
    }

}
