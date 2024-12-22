package Warriors91I;

import Doctrina.*;
import Doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends ControllableEntity {
    private static final String SPRITE_PATH = "images/player_sprites.png";

    private static final int ANIMATION_SPEED = 8;
    private static final int DAMAGE = 1;
    private static final int SHOOT_COOLDOWN = 250;

    private long lastShootTime = 0;
    private GamePad gamePad;

    private BufferedImage image;
    private Image[] leftFrames;
    private Image[] attackRightFrames;
    private Image[] attackLeftFrames;

    private int health = 100;
    private int shield = 0;
    private final int MAX_SHIELD = 100;
    private int currentAnimationFrame = 1;
    private int nextFrame = ANIMATION_SPEED;
    private boolean isAttacking = false;
    private int attackFrame = 0;
    private boolean isDead = false;

    private boolean jump = false;
    private int animationStep = 1;

    public Player(MovementController controller) {
        super(controller);
        setDimension(30, 30);
        setSpeed(15);
        teleport(300, 300);
        load();
        gamePad = new GamePad();
    }

    private void load() {
        loadSpriteSheet();
        loadAnimationFrames();
    }
    public void die() {
        this.health = 0;
    }

    private void loadAnimationFrames() {
        int frameWidth = 32;
        int frameHeight = 32;

        leftFrames = new Image[3];
        setAnimationArray(leftFrames, 0, 4, 3, frameWidth, frameHeight);

        attackRightFrames = new Image[8];
        setAnimationArray(attackRightFrames, 0, 8, 8, frameWidth, frameHeight);

        attackLeftFrames = new Image[8];
        setAnimationArray(attackLeftFrames, 0, 8, 8, frameWidth, frameHeight);
    }

    private void setAnimationArray(Image[] array, int startX, int startY, int size, int frameWidth, int frameHeight) {
        for (int i = 0; i < size; i++) {
            array[i] = image.getSubimage((startX + i) * frameWidth, startY * frameHeight, frameWidth, frameHeight);
        }
    }

    private void loadSpriteSheet() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        super.update();
        moveWithController();
        isAttacking();
        setAttackFramesAnimation();
        setFrames();
        isPlayerDead();
    }

    private void isPlayerDead() {
        if(health<=0){
            isDead = true;
        }
    }
    public boolean dead(){
        return isDead;
    }

    private void setFrames() {
        if (!isAttacking && hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                currentAnimationFrame += animationStep;
                if (currentAnimationFrame == 0 || currentAnimationFrame >= leftFrames.length - 1) {
                    animationStep *= -1;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 1;
        }
    }

    private void setAttackFramesAnimation() {
        if (isAttacking) {
            if (attackFrame < 7) {
                attackFrame++;
            } else {
                isAttacking = false;
            }
        }
    }

    private void isAttacking() {
        if (gamePad.isAttackPressed() && !isAttacking) {
            isAttacking = true;
            attackFrame = 0;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Image frameToDraw = leftFrames[currentAnimationFrame];
        if (isAttacking) {
            switch (getDirection()) {
                case LEFT:
                    frameToDraw = attackLeftFrames[attackFrame];
                    canvas.drawFlippedImage(frameToDraw, x, y, getWidth() * 2, getHeight() * 2);
                    break;
                case RIGHT:
                    frameToDraw = attackRightFrames[attackFrame];
                    canvas.drawImage(frameToDraw, x, y, getWidth() * 2, getHeight() * 2);
                    break;
            }
        } else {
            if (getDirection() == Direction.LEFT) {
                frameToDraw = leftFrames[currentAnimationFrame];
                canvas.drawFlippedImage(frameToDraw, x, y, getWidth() * 2, getHeight() * 2);
            } else {
                canvas.drawImage(frameToDraw, x, y, getWidth() * 2, getHeight() * 2);
            }
        }

    }



    public void drawHealthBar(Canvas canvas, Camera camera) {
        int barWidth = 200;
        int barHeight = 10;

        int x = camera.getX() + 10;
        int y = camera.getY() + 20 ;

        canvas.drawRectangle(x, y, barWidth + 1, barHeight + 1, Color.BLACK);
        canvas.drawRectangle(x, y, barWidth, barHeight, Color.RED);
        canvas.drawRectangle(x, y, (int) ((getHealth() / (float) 100) * barWidth), barHeight, Color.GREEN);
    }

    public void drawShieldBar(Canvas canvas, Camera camera) {
        int barWidth = 200;
        int barHeight = 10;

        int x = camera.getX() + 10;
        int y = camera.getY() + 40 ;

        canvas.drawRectangle(x, y, barWidth + 1, barHeight + 1, Color.BLACK);
        canvas.drawRectangle(x, y, barWidth, barHeight, Color.BLUE);
        canvas.drawRectangle(x, y, (int) ((getShield() / (float) MAX_SHIELD) * barWidth), barHeight, Color.CYAN);
    }

    public void applyDamage(int damage) {
        if (shield > 0) {
            applyShieldDamage(damage);
        } else {
            health -= damage;
        }
    }

    public void applyShieldDamage(int damage) {
        if (shield > 0) {
            shield -= damage;
            if (shield < 0) shield = 0; // Ensure shield doesn't go below 0
        }
    }

    public int getHealth() {
        return health;
    }

    public int getShield() {
        return shield;
    }

    public int getAttackDamage() {
        return DAMAGE;
    }

    public Ball shoot() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime >= SHOOT_COOLDOWN) {
            lastShootTime = currentTime;
            return new Ball(this);
        }
        return null;
    }

    public void setHealth() {
        health = 100;
    }

    public void isAlive() {
        isDead = false;
    }
}
