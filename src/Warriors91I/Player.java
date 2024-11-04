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

    private GamePad gamePad;


    private BufferedImage image;
    private Image[] rightFrames;
    private Image[] leftFrames;
    private Image[] upFrames;
    private Image[] downFrames;

    private int currentAnimationFrame = 1;
    private int nextFrame = ANIMATION_SPEED;

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

        downFrames = new Image[3];
        downFrames[0] = image.getSubimage(0, 0, frameWidth, frameHeight);
        downFrames[1] = image.getSubimage(frameWidth, 0, frameWidth, frameHeight);
        downFrames[2] = image.getSubimage(2 * frameWidth, 0, frameWidth, frameHeight);

        leftFrames = new Image[3];
        leftFrames[0] = image.getSubimage(0, frameHeight, frameWidth, frameHeight);
        leftFrames[1] = image.getSubimage(frameWidth, frameHeight, frameWidth, frameHeight);
        leftFrames[2] = image.getSubimage(2 * frameWidth, frameHeight, frameWidth, frameHeight);

        rightFrames = new Image[3];
        rightFrames[0] = image.getSubimage(0, 2 * frameHeight, frameWidth, frameHeight);
        rightFrames[1] = image.getSubimage(frameWidth, 2 * frameHeight, frameWidth, frameHeight);
        rightFrames[2] = image.getSubimage(2 * frameWidth, 2 * frameHeight, frameWidth, frameHeight);

        upFrames = new Image[3];
        upFrames[0] = image.getSubimage(0, 3 * frameHeight, frameWidth, frameHeight);
        upFrames[1] = image.getSubimage(frameWidth, 3 * frameHeight, frameWidth, frameHeight);
        upFrames[2] = image.getSubimage(2 * frameWidth, 3 * frameHeight, frameWidth, frameHeight);
    }

    private void loadSpriteSheet() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void update() {
        super.update();
        moveWithController();

        if (hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                currentAnimationFrame += animationStep;
                if (currentAnimationFrame == 0 || currentAnimationFrame >= leftFrames.length - 1) {
                    animationStep *= -1;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 1; // Idle state
        }
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawImage(downFrames[currentAnimationFrame], x, y, 64 * 2, 40 * 2);
    }
}
