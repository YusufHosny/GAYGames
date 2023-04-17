package games.runner;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gaygames.R;
import com.example.gaygames.ui.gamesui.runner.RunnerActivity;


public class Obstacle {

    private final ImageView img;

    private float position;

    private final RunnerActivity activity;

    private float runnerPos;

    // boolean: True if obstacle still hasnt passed the player, false otherwise
    private boolean isBefore;

    public Obstacle(RunnerActivity act) {
        // create the image
        img = new ImageView(act);
        img.setImageResource(android.R.drawable.editbox_dropdown_light_frame);
        // set image size
        img.setLayoutParams(new ViewGroup.LayoutParams(100,300));
        img.setScaleType(ImageView.ScaleType.FIT_XY);
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

        activity = act;

        runnerPos = 0;

        // by default obstacle still hasnt passed runner
        isBefore = true;



    }

    public void next(float speed) {
        // update position
        position -= speed * RunnerActivity.deltaT * 0.001;
        img.setX(position);

    }

    public boolean checkCollision(Runner runner) {
        // if first check
        if(runnerPos == 0) {
            runnerPos = runner.getPosition();
            return false;

        } else if (!isBefore) { // if obstacle passed the runner then no collision
            return false;
        } else if (position < runnerPos) { // check if position is after runner position
            isBefore = false;
            return true;
        }

        return false;

    }

    public float getPosition() {
        return position;
    }
}
