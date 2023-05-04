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

public class UserData {

    private static HashMap<AppCompatActivity, Leaderboard> leaderboardHashMap;

    private static int accountID;

    private static ArrayList<Integer> friendsList;

    public static ArrayList<Integer> getFriendsList(){
        return friendsList;
    }


    public static void initialize(int ID, AppCompatActivity a) {
        accountID = ID;
        updateFriendsList(a);
    }

    public static void updateFriendsList(AppCompatActivity a) {
        RequestQueue requestQueue = Volley.newRequestQueue(a);
        StringRequest submitRequest = new StringRequest(Request.Method.GET,
                "https://studev.groept.be/api/a22pt107/getFriendString/" + accountID,
                response -> {
                    try {
                        JSONArray responseArray = new JSONArray(response);
                        JSONObject object = responseArray.getJSONObject(0);
                        String friendString = object.getString("friendList");
                        parseFriendString(friendString);
                    } catch( JSONException e ) {
                        Log.e( "LeaderboardDB", e.getMessage(), e );
                    }
                },
                error -> Log.e( "LeaderboardDB", error.getLocalizedMessage(), error )
        );
        requestQueue.add(submitRequest);
    }

    public static void parseFriendString(String friendString) {
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

    public static void addLeaderboard(AppCompatActivity a) {
        leaderboardHashMap.put(a, new Leaderboard());
    }

    public static Leaderboard getLeaderboard(AppCompatActivity a) {
        return leaderboardHashMap.get(a);
    }

    public static void updateLeaderboard(AppCompatActivity a) {
        Objects.requireNonNull(leaderboardHashMap.get(a)).update(a);
    }

    public static void setUpdateURL(AppCompatActivity a, String url) {
        Objects.requireNonNull(leaderboardHashMap.get(a)).setRequestURL(url);
    }
    public static void setAddURL(AppCompatActivity a, String url) {
        Objects.requireNonNull(leaderboardHashMap.get(a)).setAddScoreURL(url);
    }

}
