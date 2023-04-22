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

    private final float initPos;
    private float runnerPos;

    // boolean: True if obstacle still hasnt passed the player, false otherwise
    private boolean isBefore;

    public Obstacle(RunnerActivity act) {
        // create the image
        img = new ImageView(act);
        img.setImageResource(android.R.drawable.editbox_dropdown_light_frame);
        // set image size
        img.setLayoutParams(new ViewGroup.LayoutParams(100,240));
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        // add image to activity
        ((ConstraintLayout) act.findViewById(R.id.RunnerBg)).addView(img);


        // set image position to just out of the screen, with the right height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        initPos = screenWidth - 10;

        position = initPos;

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

        if(getPosition() < -100f) { // if out of screen move to start of screen
           setPosition(initPos);
           isBefore = true;
        }

    }

    public boolean checkCollision(Runner runner) {
        // start and end positions of the runner img
        float rPosStart, rPosEnd;
        rPosEnd = runnerPos + 50f;
        rPosStart = rPosEnd + 100f;


        // if first check
        if(runnerPos == 0) {
            runnerPos = runner.getX();
            return false;

        } else if (!isBefore) { // if obstacle passed the runner then no collision
            return false;
        } else if (position < rPosEnd) { // check if position is after runner position
            isBefore = false;
            activity.incrementScore();
        } else if (position < rPosStart) { // check if position is between rpos start and end
            return runner.getPosition() > 375f; // check if height is higher than obstacle
        }

        return false;

    }

    public float getPosition() {
        return position;
    }

    public void setPosition(float p) {
        position = p;
        img.setX(position);
    }
}
