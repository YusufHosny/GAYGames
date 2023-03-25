package games.general;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

public class GameButton {
    // Button that opens this game
    private Button button;
    // Game class which has the activity and start method
    private Game game;

    public GameButton(Button b, Game g) {
        this.button = b;
        this.game = g;
        // When the button is clicked
        b.setOnClickListener(view -> {
            // Get the context of the button i.e. the Activity the button was in
            Context context = view.getContext();
            // Create an intent for switching to the game activity
            Intent intent = new Intent(context, g.getActiviy());
            // Start the activity
            context.startActivity(intent);
        });
    }


}
