package Warriors91I;

import Doctrina.Canvas;
import Doctrina.Direction;
import Doctrina.MovableEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Enemy extends MovableEntity {
    private static final int CHANGE_DIRECTION_THRESHOLD = 100;
    private static final String SPRITE_PATH = "images/player_sprites.png";
    private static final int FRAME_WIDTH = 64;
    private static final int FRAME_HEIGHT = 40;
    private static final int FRAME_COUNT = 3;
    private static final int ATTACK_COOLDOWN = 500;
    private long lastAttackTime = 0;
    private static final int DAMAGE =1;
    private int health = 100;
    private int moveCounter = 0;
    private int currentAnimationFrame = 0;
    private int animationTimer = 0;

    private boolean dead = false;
    private static final int MIN_ATTACK_DISTANCE = 30;

    private BufferedImage spriteSheet;
    private final Image[] rightFrames = new Image[FRAME_COUNT];
    private final Image[] leftFrames = new Image[FRAME_COUNT];
    private final Image[] upFrames = new Image[FRAME_COUNT];
    private final Image[] downFrames = new Image[FRAME_COUNT];

    public Enemy(int x, int y) {
        super();
        setDimension(32, 32);
        teleport(x, y);
        setSpeed(3);
        loadSprites();
    }
    public void moveTowardsPlayer(Player player) {
        int playerX = player.getX();
        int playerY = player.getY();

        int distanceX = Math.abs(playerX - this.getX());

        if (distanceX > MIN_ATTACK_DISTANCE) {
            if (playerX > this.getX()) {
                setDirection(Direction.RIGHT);
            } else if (playerX < this.getX()) {
                setDirection(Direction.LEFT);
            }
            move();
            setSpeed(3);
        } else {
            stopMoving();
        }
    }

    private void stopMoving() {
            setSpeed(0);
    }

    public void hitPlayer(Player player) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime >= ATTACK_COOLDOWN) {
            if (intersectWith(player)) {
                player.applyDamage(DAMAGE);
                lastAttackTime = currentTime;
            }
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
        canvas.drawImage(currentFrames[currentAnimationFrame], x, y, FRAME_WIDTH * 2, FRAME_HEIGHT * 2);
        drawHealthBar( canvas);
    }

    private void loadSprites() {
        try {
            spriteSheet = ImageIO.read(getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
            loadAnimationFrames( leftFrames);
            loadAnimationFrames( rightFrames);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du sprite sheet : " + e.getMessage());
        }
    }

    private void loadAnimationFrames( Image[] frames) {
        int directionRow = 1;

        for (int i = 0; i < FRAME_COUNT; i++) {
            frames[i] = spriteSheet.getSubimage(i * FRAME_WIDTH, directionRow * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT);
        }
    }

    private void updateAnimation() {
        animationTimer++;
        if (animationTimer >= 10) { // Change de frame tous les 10 ticks
            currentAnimationFrame = (currentAnimationFrame + 1) % FRAME_COUNT;
            animationTimer = 0;
        }
    }

    private void changeDirection() {
        Direction[] directions ={Direction.RIGHT, Direction.LEFT} ;

        int randomIndex = (int) (Math.random() * directions.length);

        setDirection(directions[randomIndex]);
    }

    private Image[] getCurrentAnimationFrames() {
        return switch (getDirection()) {
            case UP -> upFrames;
            case DOWN -> downFrames;
            case LEFT -> leftFrames;
            case RIGHT -> rightFrames;
            default -> throw new IllegalStateException("Direction inconnue : " + getDirection());
        };
    }
    public void drawHealthBar(Canvas canvas) {
        int barWidth = width;
        int barHeight = 5;

        int x = getX()+ 50;
        int y = getY() ;

        canvas.drawRectangle(x, y, barWidth + 1, barHeight + 1, Color.BLACK);
        canvas.drawRectangle(x, y, barWidth, barHeight, Color.RED);
        canvas.drawRectangle(x, y, (int) ((health / (float) 100) * barWidth), barHeight, Color.GREEN);
    }


    public boolean isDead() {
        return dead;
    }

    public void takeDamage(int attackDamage) {
        health -= attackDamage;
        if (health<0){
            dead = true;
        }
    }
}
