package games.general;

import android.content.Context;
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
import java.util.LinkedHashMap;
import java.util.Map;

public class Leaderboard {

    private String requestURL;
    private String addScoreURL;
    private final HashMap<String, Integer> leaderboard; // name and score key value pair

    Leaderboard() {
        leaderboard = new HashMap<>();
    }


    // update the leaderboard to the latest data
    public void update(AppCompatActivity c) {
        RequestQueue requestQueue = Volley.newRequestQueue(c);
        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL,
                response -> {
                    try {
                        JSONArray responseArray = new JSONArray(response);

                        for( int i = 0; i < responseArray.length(); i++ )
                        {
                            // for each row, add it to leaderboard hashmap
                            JSONObject curObject = responseArray.getJSONObject( i );
                            leaderboard.put(curObject.getString("playerName"),
                                    curObject.getInt("score"));
                        }
                    } catch( JSONException e ) {
                        Log.e( "LeaderboardDB", e.getMessage(), e );
                    }
                },
                error -> Log.e( "LeaderboardDB", error.getLocalizedMessage(), error )
        );
        requestQueue.add(submitRequest);

    }


    // sort leaderboard
    public LinkedHashMap<String, Integer> getSortedLeaderboard() {
        // create new arraylist of entries
        ArrayList<Map.Entry<String, Integer>> entryList = new ArrayList<>(leaderboard.entrySet());


        // sort arraylist with comparator that sorts by value, then key for same values
        entryList.sort((e1, e2) -> {
            if (e1.getValue().equals(e2.getValue())) {
                return - e1.getKey().compareTo(e2.getKey());
            }
            return - e1.getValue().compareTo(e2.getValue());
        });

        // add arraylist values in order to linked hashmap
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        entryList.forEach(e -> sortedMap.put(e.getKey(), e.getValue()));

        return sortedMap;
    }


    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void setAddScoreURL(String addScoreURL) {
        this.addScoreURL = addScoreURL;
    }
}

