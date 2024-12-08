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

    private static final int SHOOT_COOLDOWN = 250; // Cooldown en millisecondes
    private long lastShootTime = 0; // Temps du dernier tir

    private GamePad gamePad;

    private BufferedImage image;
    private Image[] rightFrames;
    private Image[] leftFrames;
    private Image[] upFrames;
    private Image[] downFrames;
    private Image[] attackRightFrames;
    private Image[] attackLeftFrames;

    private int health = 100;

    private int currentAnimationFrame = 1;
    private int nextFrame = ANIMATION_SPEED;
    private boolean isAttacking = false;
    private int attackFrame = 0;
    private static final int ATTACK_ANIMATION_SPEED = 10;

    private boolean jump = false;
    private int animationStep = 1;

    public Player(MovementController controller) {
        super(controller);
        setDimension(32, 32);
        setSpeed(10);
        teleport(300, 300);
        load();
        gamePad = new GamePad();
    }

    private void load() {
        loadSpriteSheet();
        loadAnimationFrames();
    }

    private void loadAnimationFrames() {
        int frameWidth = 64;
        int frameHeight = 40;

        leftFrames = new Image[3];
        leftFrames[0] = image.getSubimage(0, frameHeight, frameWidth, frameHeight);
        leftFrames[1] = image.getSubimage(frameWidth, frameHeight, frameWidth, frameHeight);
        leftFrames[2] = image.getSubimage(2 * frameWidth, frameHeight, frameWidth, frameHeight);

        attackRightFrames = new Image[3];
        attackRightFrames[0] = image.getSubimage(0, 6 * frameHeight, frameWidth, frameHeight);
        attackRightFrames[1] = image.getSubimage(frameWidth, 6 * frameHeight, frameWidth, frameHeight);
        attackRightFrames[2] = image.getSubimage(2 * frameWidth, 6 * frameHeight, frameWidth, frameHeight);

        attackLeftFrames = new Image[3];
        attackLeftFrames[0] = image.getSubimage(0, 5 * frameHeight, frameWidth, frameHeight);
        attackLeftFrames[1] = image.getSubimage(frameWidth, 5 * frameHeight, frameWidth, frameHeight);
        attackLeftFrames[2] = image.getSubimage(2 * frameWidth, 5 * frameHeight, frameWidth, frameHeight);
    }

    private void loadSpriteSheet() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
        } catch (IOException e) {
        }
    }

    @Override
    public void update() {
        super.update();
        moveWithController();
        isAttacking();
        setAttackFramesAnimation();
        setFrames();
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
            if (attackFrame < 2) {
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
        if (isAttacking) {
            switch (getDirection()) {
                case LEFT:
                    canvas.drawImage(attackLeftFrames[attackFrame], x, y, 97, 157);
                    break;
                case RIGHT:
                    canvas.drawImage(attackRightFrames[attackFrame], x, y, 64 * 2, 40 * 2);
                    break;
            }
        } else {
            canvas.drawImage(leftFrames[currentAnimationFrame], x, y, 64*2, 40*2 );
        }
        drawPlayerStats(canvas);


    }
    private void drawPlayerStats(Canvas canvas) {

    }


    public void drawHealthBar(Canvas canvas) {
        int barWidth = 200;
        int barHeight = 10;

        int x = 10;
        int y = 10;


        canvas.drawRectangle(x, y, barWidth + 1, barHeight + 1, Color.BLACK);
        canvas.drawRectangle(x, y, barWidth, barHeight, Color.RED);
        canvas.drawRectangle(x, y, (int) ((getHealth() / (float) 100) * barWidth), barHeight, Color.GREEN);




    }

    public void applyDamage(int damage) {
        health = health - damage;
    }

    public int getHealth() {
        return health;
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
}
