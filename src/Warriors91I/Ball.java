package Warriors91I;

import Doctrina.Canvas;
import Doctrina.Direction;
import Doctrina.MovableEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ball extends MovableEntity {
    private final Direction playerDirection;
    private static final long LIFE_SPAN = 5000;
    private final long creationTime;
    private int speed;
    private boolean isOutOfBounds = false;
    private String AMMO_PATH="images/Ammo.png";
    private BufferedImage image;
    private Image[] ammoAnimations;

    public Ball(Player player) {
        playerDirection = player.getDirection();
        creationTime = System.currentTimeMillis(); // Initialisation du temps de création
        initialize(player);
        load();
        speed = 6;
    }
    public Ball(Player player, int speed) {
        playerDirection = player.getDirection();
        creationTime = System.currentTimeMillis(); // Initialisation du temps de création
        initialize(player);
        this.speed = speed;
    }
    private void load() {
        loadAnimationFrames();

        loadSpriteSheet();

    }

    private void loadAnimationFrames() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(AMMO_PATH));
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du sprite sheet : " + e.getMessage());
        }
    }

    private void loadSpriteSheet() {
        ammoAnimations = new Image[4];
        ammoAnimations[0] = image.getSubimage(0, 50*2, 50, 50);

    }




    private void initialize(Player player) {
        setSpeed(speed);

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
        canvas.drawImage(ammoAnimations[0],x,y);
    }

    public boolean getIsOutOfBounds() {
        return this.isOutOfBounds;
    }
}
