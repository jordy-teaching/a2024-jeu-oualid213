package Warriors91I;

import Doctrina.Canvas;

import java.awt.*;

public class Monster extends Enemy{

    public Monster(int x, int y) {
        super(x, y);
        DAMAGE = 20;

        speed = 5;
        SPRITE_PATH ="images/Monster.png";
        loadSprites();
    }

    @Override
    protected void loadAnimationFrames() {
        leftFrames[0] = spriteSheet.getSubimage(128 * 0 + 50, 128, 128, 128);
        leftFrames[1] = spriteSheet.getSubimage(128 * 1, 32 *0, 128, 128);
        leftFrames[2] = spriteSheet.getSubimage(128 * 2, 32 *0, 128, 128);

        rightFrames[0] = spriteSheet.getSubimage(128 * 0, 128 * 1, 128, 128);
        rightFrames[1] = spriteSheet.getSubimage(128 * 1, 128 * 1, 128, 128);
        rightFrames[2] = spriteSheet.getSubimage(128 * 2, 128 * 1, 128, 128);
    }
    @Override
    public void draw(Canvas canvas) {
        Image[] currentFrames = getCurrentAnimationFrames();
        canvas.drawImage(leftFrames[0], getX(), getY(), 128  , 128);
        drawHealthBar(canvas);
    }
}
