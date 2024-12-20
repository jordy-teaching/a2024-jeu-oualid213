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

    private void loadAnimationFrames() {
        int frameWidth = 32;
        int frameHeight = 32;

        leftFrames = new Image[3];
        leftFrames[0] = image.getSubimage(0, 4*frameHeight, frameWidth, frameHeight);
        leftFrames[1] = image.getSubimage(frameWidth, 4*frameHeight, frameWidth, frameHeight);
        leftFrames[2] = image.getSubimage(2 * frameWidth, 4*frameHeight, frameWidth, frameHeight);

        attackRightFrames = new Image[8];
        attackRightFrames[0] = image.getSubimage(frameWidth * 0, 8 *frameHeight, frameWidth, frameHeight);
        attackRightFrames[1] = image.getSubimage(frameWidth * 1 , 8* frameHeight, frameWidth, frameHeight);
        attackRightFrames[2] = image.getSubimage(2 * frameWidth, 8 * frameHeight, frameWidth, frameHeight);
        attackRightFrames[3] = image.getSubimage(3 * frameWidth, 8 * frameHeight, frameWidth, frameHeight);
        attackRightFrames[4] = image.getSubimage(4 * frameWidth, 8 * frameHeight, frameWidth, frameHeight);
        attackRightFrames[5] = image.getSubimage(5 * frameWidth, 8 * frameHeight, frameWidth, frameHeight);
        attackRightFrames[6] = image.getSubimage(6 * frameWidth, 8 * frameHeight, frameWidth, frameHeight);
        attackRightFrames[7] = image.getSubimage(7 * frameWidth, 8 * frameHeight, frameWidth, frameHeight);

        attackLeftFrames = new Image[8];
        attackLeftFrames[0] = image.getSubimage(frameWidth * 0, 8 *frameHeight, frameWidth, frameHeight);
        attackLeftFrames[1] = image.getSubimage(frameWidth * 1 , 8* frameHeight, frameWidth, frameHeight);
        attackLeftFrames[2] = image.getSubimage(2 * frameWidth, 8 * frameHeight, frameWidth, frameHeight);
        attackLeftFrames[3] = image.getSubimage(3 * frameWidth, 8 * frameHeight, frameWidth, frameHeight);
        attackLeftFrames[4] = image.getSubimage(4 * frameWidth, 8 * frameHeight, frameWidth, frameHeight);
        attackLeftFrames[5] = image.getSubimage(5 * frameWidth, 8 * frameHeight, frameWidth, frameHeight);
        attackLeftFrames[6] = image.getSubimage(6 * frameWidth, 8 * frameHeight, frameWidth, frameHeight);
        attackLeftFrames[7] = image.getSubimage(7 * frameWidth, 8 * frameHeight, frameWidth, frameHeight);


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


        drawPlayerStats(canvas);
        drawHealthBar(canvas);
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


/*
* continuer a faire les autres ennemi et les mettre dans le bonne endroi
* faire     private void isAttacking() {
        if (gamePad.isAttackPressed() && !isAttacking) {
            isAttacking = true;
            attackFrame = 0;
        }
    }
un beau UI pour le menu */