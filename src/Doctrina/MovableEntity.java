package Doctrina;

import java.awt.*;

public abstract class MovableEntity extends StaticEntity {
    private int speed = 100;
    private Direction direction = Direction.DOWN;
    private final Collision collision;

    private int lastX = Integer.MIN_VALUE;
    private int lastY = Integer.MIN_VALUE;

    private boolean inAir = true;
    private boolean inAirPlatform = false;
    private float verticalVelocity = 0;

    protected Physics physics;

    private boolean moved;

    public void update() {
        moved = false;
    }

    public MovableEntity() {
        collision = new Collision(this);
    }

    public void move() {
        int allowedSpeed = collision.getAllowedSpeed(direction);


        x += direction.calculateVelocityX(allowedSpeed);
        y += direction.calculateVelocityY(allowedSpeed);

        moved = (x != lastX || y != lastY);

        lastX = x;
        lastY = y;
    }

    public boolean isInAirPlatform() {
        return inAirPlatform;
    }
    public void setInAirPlatform( boolean inAirPlatform){
        this.inAirPlatform = inAirPlatform;
    }

    public boolean hasMoved() {
        return moved;
    }

    public void move(Direction direction) {
        this.direction = direction;
        move();
    }

    public Rectangle getHitBox() {
        return switch (direction) {
            case UP -> getUpperHitBox();
            case DOWN -> getLowerHitBox();
            case LEFT -> getLeftHitBox();
            case RIGHT -> getRightHitBox();
        };
    }

    private Rectangle getAnyHitBox() {
        return new Rectangle(x + 50 , y + 32, width, height);
    }

    private Rectangle getUpperHitBox() {
        return new Rectangle(x + 50 , y, width, height);
    }

    private Rectangle getLowerHitBox() {
        return new Rectangle(x + 50 , y, width, height);
    }

    private Rectangle getLeftHitBox() {
        return new Rectangle(x + 40 , y, width , height);
    }

    private Rectangle getRightHitBox() {
        return new Rectangle(x + 60 , y, width, height);
    }

    public boolean hitBoxIntersectWith(StaticEntity other) {
        if (other == null) {
            return false;
        }
        return getHitBox().intersects(other.getBounds());
    }

    public void drawHitBox(Canvas canvas) {
        Rectangle rect = getHitBox();
        canvas.drawRectangle(rect.x, rect.y, rect.width, rect.height, Color.BLUE);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setVerticalVelocity(float verticalVelocity) {
        this.verticalVelocity = verticalVelocity;
    }

    public float getVerticalVelocity() {
        return verticalVelocity;
    }

    public void setInAir(boolean b) {
        this.inAir = b;
    }

    public boolean isInAir() {
        return inAir;
    }
}
