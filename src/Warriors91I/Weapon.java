package Warriors91I;

import Doctrina.Canvas;
import Doctrina.Direction;
import Doctrina.MovableEntity;

import java.awt.*;

public class Weapon extends MovableEntity {
    private final Direction playerDirection;
    private static final long LIFE_SPAN = 5000;
    private final long creationTime;
    private boolean isOutOfBounds = false;

    public Weapon(Player player) {
        playerDirection = player.getDirection();
        creationTime = System.currentTimeMillis(); // Initialisation du temps de création
        initialize(player);
    }

    private void initialize(Player player) {
        setSpeed(5);

        switch (playerDirection) {
            case RIGHT:
                setDimension(8, 4);
                teleport(
                        player.getX() + player.getWidth() + 1,
                        player.getY() + player.getHeight() / 2 - height / 2
                );
                break;
            case LEFT:
                setDimension(8, 4);
                teleport(
                        player.getX() - 9,
                        player.getY() + player.getHeight() / 2 - height / 2
                );
                break;
            case DOWN:
                setDimension(4, 8);
                teleport(
                        player.getX() + player.getWidth() / 2 - width / 2,
                        player.getY() + player.getHeight() + 1
                );
                break;
            case UP:
                setDimension(4, 8);
                teleport(
                        player.getX() + player.getWidth() / 2 - width / 2,
                        player.getY() - height - 1
                );
                break;
        }
    }

    @Override
    public void update() {
        move(playerDirection,12);

        if (System.currentTimeMillis() - creationTime > LIFE_SPAN) {
            isOutOfBounds = true;
        }
    }

    public void isOutOfBounds(boolean isOutOfBounds) {
        this.isOutOfBounds = isOutOfBounds;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRectangle(this, Color.YELLOW);
    }

    public boolean getIsOutOfBounds() {
        return this.isOutOfBounds;
    }
}
