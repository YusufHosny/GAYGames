package games.general;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageButton;

public class GameButton {

    public GameButton(ImageButton b, Game g) {
        // Button that opens this game
        // Game class which has the activity and start method

        // When the button is clicked
        b.setOnClickListener(view -> {
            // Get the context of the button i.e. the Activity the button was in
            Context context = view.getContext();
            // Create an intent for switching to the game activity
            Intent intent = new Intent(context, g.getActivity());
            // Start the activity
            context.startActivity(intent);
        });
    }


}
