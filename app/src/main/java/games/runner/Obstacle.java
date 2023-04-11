package games.runner;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gaygames.ui.gamesui.runner.RunnerActivity;
import com.example.gaygames.R;


public class Obstacle {

    private final ImageView img;

    private float position;


    public Obstacle(AppCompatActivity act) {
        // create the image
        img = new ImageView(act);
        img.setImageResource(android.R.drawable.editbox_dropdown_light_frame);
        // set image size
        img.setLayoutParams(new ViewGroup.LayoutParams(100,300));
        // add image to activity
        ((ConstraintLayout) act.findViewById(R.id.RunnerBg)).addView(img);


        // set image position to just out of the screen, with the right height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        position = screenWidth - 10;

        img.setX(position);
        img.setY(screenHeight * 0.5f);


    }

    public void next(float speed) {
        // update position
        position -= speed * RunnerActivity.deltaT * 0.001;
        img.setX(position);

    }

    public float getPosition() {
        return position;
    }
}
