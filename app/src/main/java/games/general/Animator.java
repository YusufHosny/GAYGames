package games.general;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Animator {

    ScheduledFuture<?> f;

    ArrayList<Animatable> animatables;

    ArrayList<Animatable> addQueue;

    int deltaT;

    public Animator(int deltaT) {
        this.deltaT = deltaT;
        animatables = new ArrayList<>();
        addQueue = new ArrayList<>();
        start();
    }

    public void add(Animatable animatable) {
        addQueue.add(animatable);
    }

    public void animate() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        f = executor.scheduleAtFixedRate(this::nextFrame, 2 * deltaT, deltaT, TimeUnit.MILLISECONDS);
    }

    public void terminate() {
        f.cancel(true);
    }

    public void nextFrame() {
        for (Animatable animatable: addQueue) {
            animatables.add(animatable);
            addQueue.remove(animatable);
        }

        for (Animatable animatable: animatables) {
            animatable.repeat();
        }
    }

    public void start() {
        for (Animatable animatable: animatables) {
            animatable.start();
        }
    }

}
