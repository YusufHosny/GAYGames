package games.runner;

import android.widget.ImageView;

import com.example.gaygames.ui.gamesui.runner.RunnerActivity;

import games.general.Animatable;

public class ParallaxBackground implements Animatable {

    ScrollImageView layer1, layer2, layer3;

    public ParallaxBackground(ImageView l1A, ImageView l1B, ImageView l2A, ImageView l2B,
                              ImageView l3A, ImageView l3B, int spdMax) {

        layer1 = new ScrollImageView(l1A, l1B, spdMax/4);
        layer2 = new ScrollImageView(l2A, l2B, spdMax/2);
        layer3 = new ScrollImageView(l3A, l3B, spdMax);
    }

    public void updateSpd() {
        int spd = RunnerActivity.scrollSpeed;

        layer1.setSpd(spd/4);
        layer2.setSpd(spd/2);
        layer3.setSpd(spd);
    }

    public void repeat() {
        updateSpd();

        layer1.repeat();
        layer2.repeat();
        layer3.repeat();
    }

}
