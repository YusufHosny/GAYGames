package games.runner;

import com.example.gaygames.ui.gamesui.runner.RunnerActivity;

import java.util.ArrayList;

public class Obstacles {

    private final ArrayList<Obstacle> obstacles;

    private final ObstacleFactory obstacleFactory;

    private final RunnerActivity activity;



    public Obstacles(RunnerActivity a) {
        activity = a;
        obstacleFactory = new ObstacleFactory(a);
        obstacles = new ArrayList<>();

    }


    public void addObstacle() {
        obstacles.add(obstacleFactory.createObstactle());
    }

    // runs the next for reach obstacle and checks if its out of the screen to remove it
    public void next() {
        for(Obstacle obstacle: obstacles)
        {
            obstacle.next(activity.getScrollSpeed());

            if(obstacle.getPosition() < 0) {
                obstacles.remove(obstacle);
            }
        }
    }

}
