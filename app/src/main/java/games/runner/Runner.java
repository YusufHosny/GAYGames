package games.runner;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gaygames.ui.gamesui.runner.RunnerActivity;

public class Runner {

    private float position;

    private final float ground;

    private final ImageView img;

    private int vertSpeed;


    public Runner(ImageView i) {
        img = i;

        // set image size
        img.setLayoutParams(new ViewGroup.LayoutParams(200,200));


        // set ground and initial pos
        position = img.getY();

        ground = position;


    }

    public void move(int offset) {

    }

    // next frame updates for runner object
    public void next() {
        // update position, done with a - since the position starts from the top
        position -= vertSpeed * RunnerActivity.deltaT * 0.001;

        // clip position to ground
        position = Math.min(position, ground);

        img.setY(position);

        // update speed
        vertSpeed -= RunnerActivity.gravity;




    }

    public void jump() {
        if(position == ground) {
            vertSpeed = 500;
        }
    }

    public void duck() {}


    public float getPosition() {
        return position;
    }
}
