package Warriors91I.Entities;

import Doctrina.Canvas;
import Doctrina.Direction;
import Doctrina.MovableEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Enemy extends MovableEntity {
    private static final int CHANGE_DIRECTION_THRESHOLD = 100;
    protected   String SPRITE_PATH;
    protected static final int FRAME_WIDTH = 32;
    protected static final int FRAME_HEIGHT = 32;
    private static final int FRAMES_PER_DIRECTION = 3;
    private static final int ANIMATION_SPEED = 8;
    private static final int ATTACK_COOLDOWN = 700;
    protected int DAMAGE ;
    private static final int MIN_ATTACK_DISTANCE = 30;

    protected long lastAttackTime = 0;
    private int health = 100;
    private int moveCounter = 0;
    protected int currentAnimationFrame = 0;
    private int animationTimer = 0;

    protected int speed;

    private boolean dead = false;

    protected BufferedImage spriteSheet;

    protected final Image[] rightFrames = new Image[FRAMES_PER_DIRECTION];
    protected final Image[] leftFrames = new Image[FRAMES_PER_DIRECTION];
    private final Image[] upFrames = new Image[FRAMES_PER_DIRECTION];
    private final Image[] downFrames = new Image[FRAMES_PER_DIRECTION];

    public Enemy(int x, int y) {
        super();
        setDimension(FRAME_WIDTH, FRAME_HEIGHT);

        teleport(x, y);
        setSpeed(speed);
    }

    public void moveTowardsPlayer(Player player) {
        int playerX = player.getX();
        int playerY = player.getY();

        int distanceX = Math.abs(playerX - this.getX());
        int distanceY = Math.abs(playerY - this.getY());

        if (distanceX > MIN_ATTACK_DISTANCE || distanceY > MIN_ATTACK_DISTANCE) {
            if (playerX > this.getX()) {
                setDirection(Direction.RIGHT);
            } else if (playerX < this.getX()) {
                setDirection(Direction.LEFT);
            } else if (playerY > this.getY()) {
                setDirection(Direction.DOWN);
            } else if (playerY < this.getY()) {
                setDirection(Direction.UP);
            }
            setSpeed((int) (speed + speed * 0.5));
            move();
        } else {
            stopMoving();
        }
    }

    private void stopMoving() {
        setSpeed(0);
    }

    public void hitPlayer(Player player) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime >= ATTACK_COOLDOWN && intersectWith(player)) {
            player.applyDamage(DAMAGE);
            lastAttackTime = currentTime;
        }
    }

    @Override
    public void update() {
        super.update();
        move();
        moveCounter++;
        if (moveCounter >= CHANGE_DIRECTION_THRESHOLD) {
            changeDirection();
            moveCounter = 0;
        }
        updateAnimation();
    }

    @Override
    public void draw(Canvas canvas) {
        Image[] currentFrames = getCurrentAnimationFrames();
        canvas.drawImage(currentFrames[currentAnimationFrame], getX(), getY(), FRAME_WIDTH +20 , FRAME_HEIGHT +20);
        drawHealthBar(canvas);
    }

    protected void loadSprites() {
        try {
            spriteSheet = ImageIO.read(getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
            loadAnimationFrames();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du sprite sheet : " + e.getMessage());
        }
    }


    private void updateAnimation() {
        if (hasMoved()) {
            animationTimer++;
            if (animationTimer >= ANIMATION_SPEED) {
                currentAnimationFrame = (currentAnimationFrame + 1) % FRAMES_PER_DIRECTION;
                animationTimer = 0;
            }
        } else {
            currentAnimationFrame = 0;
        }
    }

    private void changeDirection() {
        Direction[] directions = {Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN};
        setDirection(directions[(int) (Math.random() * directions.length)]);
    }

    protected Image[] getCurrentAnimationFrames() {
        return switch (getDirection()) {
            case LEFT -> leftFrames;
            case RIGHT -> rightFrames;
            case UP -> upFrames;
            case DOWN -> downFrames;
            default -> throw new IllegalStateException("Direction inconnue : " + getDirection());
        };
    }

    void drawHealthBar(Canvas canvas) {
        int barWidth = FRAME_WIDTH * 2;
        int barHeight = 5;

        int barX = getX();
        int barY = getY() - 10;

        canvas.drawRectangle(barX, barY, barWidth + 1, barHeight + 1, Color.BLACK);
        canvas.drawRectangle(barX, barY, barWidth, barHeight, Color.RED);
        canvas.drawRectangle(barX, barY, (int) ((health / 100.0) * barWidth), barHeight, Color.GREEN);
    }

    public boolean isDead() {
        return dead;
    }

    public void takeDamage(int attackDamage) {
        health -= attackDamage;
        if (health <= 0) {
            dead = true;
        }
    }

    protected abstract void loadAnimationFrames();
}