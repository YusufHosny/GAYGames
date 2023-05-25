package games.general;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Animator {

    ScheduledFuture<?> f;

    ArrayList<Animatable> animatables;

    int deltaT;

    public Animator(int deltaT) {
        this.deltaT = deltaT;
        animatables = new ArrayList<>();
    }

    public void add(Animatable animatable) {
        animatables.add(animatable);
    }

    public void animate() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        f = executor.scheduleAtFixedRate(this::nextFrame, 3L * deltaT, deltaT, TimeUnit.MILLISECONDS);
    }

    public void terminate() {
        f.cancel(true);
    }

    private void nextFrame() {

        for (Animatable animatable: animatables) {

            animatable.repeat();
        }
    }


}
