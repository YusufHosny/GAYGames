package games.runner;

import com.example.gaygames.ui.gamesui.runner.RunnerActivity;

public class ObstacleFactory {

    RunnerActivity activity;

    public ObstacleFactory(RunnerActivity act) {
        activity = act;
    }


    public Obstacle createObstactle() {
        return new Obstacle(activity);

    }
}
