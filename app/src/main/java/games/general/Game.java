package games.general;

import androidx.appcompat.app.AppCompatActivity;

public abstract class Game {
    // Activity associated with the game
    protected Class<? extends AppCompatActivity> act;
    public abstract void start();

    public Class<? extends AppCompatActivity> getActivity() {
        return act;
    }

}
