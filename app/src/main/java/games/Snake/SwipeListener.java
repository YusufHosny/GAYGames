package games.Snake;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.gaygames.ui.gamesui.Snake.SnakeActivity;

public class SwipeListener implements View.OnTouchListener{
    GestureDetector GestureDetector;

    public SwipeListener(View view, SnakeActivity snakeGame, TextView tv){
        int threshold = 100;
        int velocity_threshold=100;

        GestureDetector.SimpleOnGestureListener listener =
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDown(MotionEvent e){
                        return true;
                    }
                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                            float diffY = e2.getY() - e1.getY();
                            float diffX = e2.getX() - e1.getX();
                            if (Math.abs(diffX) > Math.abs(diffY)) {
                                if (Math.abs(diffX) > threshold && Math.abs(velocityX) > velocity_threshold) {
                                    if (diffX > 0) {
                                        // Swipe right
                                        snakeGame.setDirection(Direction.Right);
                                        tv.setText("RIGHT");

                                    } else {
                                        // Swipe left
                                        snakeGame.setDirection(Direction.Left);
                                        tv.setText("LEFT");
                                    }
                                    return true;
                                }
                            } else {
                                if (Math.abs(diffY) > threshold && Math.abs(velocityY) > velocity_threshold) {
                                    if (diffY > 0) {
                                        // Swipe down
                                        snakeGame.setDirection(Direction.Down);
                                        tv.setText("DOWN");
                                    } else {
                                        // Swipe up
                                        snakeGame.setDirection(Direction.Up);
                                        tv.setText("UP");
                                    }
                                }
                                return true;
                            }
                            return false;
                        }
                };
        GestureDetector = new GestureDetector(listener);
        view.setOnTouchListener(this);
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return GestureDetector.onTouchEvent(motionEvent);
    }
}
