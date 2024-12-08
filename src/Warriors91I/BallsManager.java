
package Warriors91I;

import Doctrina.Canvas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class BallsManager {
    private ArrayList<Ball> balls;

    private int ballNumber;

    public BallsManager() {
        balls = new ArrayList<>();
        this.ballNumber = new Random().nextInt(50,101);
    }
    public void addBall(Ball weapon) {
        balls.add(weapon);
    }



    public boolean isIntersect(Enemy enemy) {
        Iterator<Ball> iterator = balls.iterator();

        while (iterator.hasNext()) {
            Ball weapon = iterator.next();
            if (weapon.intersectWith(enemy)) {
                weapon.isOutOfBounds(true);
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public int numberOfBalls(){
        return ballNumber;
    }

    public void oneBallLeft(){
        ballNumber --;
    }

    public void updateWeapons() {
        Iterator<Ball> iterator = balls.iterator();
        while (iterator.hasNext()) {
            Ball weapon = iterator.next();
            weapon.update();
            if (weapon.getIsOutOfBounds()) {
                iterator.remove();
            }
        }
    }

    public void drawBalls(Canvas canvas) {
        for (Ball ball : balls) {
            ball.draw(canvas);
        }
    }
}

