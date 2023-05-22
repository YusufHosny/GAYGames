package games.runner;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gaygames.ui.gamesui.runner.RunnerActivity;

import games.general.Animatable;

public class ScrollImageView implements Animatable {

    private final ImageView scroll1, scroll2;
    private int spd;

    public ScrollImageView(ImageView view1, ImageView view2, int speed) {
        scroll1 = view1;
        scroll2 = view2;
        spd = speed;

        // set to out of bounds
        scroll2.setX(scroll1.getWidth());
    }

    public void setSpd(int newSpd) {
        spd = newSpd;
    }


    public void repeat() {
        ((AppCompatActivity) scroll2.getContext()).runOnUiThread( () -> {

            // scrolling logic
            scroll1.setX((scroll1.getX()) -spd * RunnerActivity.deltaT * 0.001f);
            scroll2.setX((scroll2.getX()) -spd * RunnerActivity.deltaT * 0.001f);

            // if out of bounds, clip to the other scrollview
            if (scroll1.getX() < -scroll1.getWidth()) scroll1.setX(scroll2.getX() + scroll2.getWidth());
            if (scroll2.getX() < -scroll2.getWidth()) scroll2.setX(scroll1.getX() + scroll1.getWidth());

        });

    }

}
