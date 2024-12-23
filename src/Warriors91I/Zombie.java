package Warriors91I;

public class Zombie extends Enemy{


    public Zombie(int x, int y) {
        super(x, y);
        speed =1;
        DAMAGE = 7;
        SPRITE_PATH =  "images/player.png";
        loadSprites();
    }

    @Override
    protected void loadAnimationFrames() {
        leftFrames[0] = spriteSheet.getSubimage(32 * 3, 32 * 5, width, height);
        leftFrames[1] = spriteSheet.getSubimage(32 * 4, 32 * 5, width, height);
        leftFrames[2] = spriteSheet.getSubimage(32 * 5, 32 * 5, width, height);

        rightFrames[0] = spriteSheet.getSubimage(32 * 3, 32 * 6, width, height);
        rightFrames[1] = spriteSheet.getSubimage(32 * 4, 32 * 6, width, height);
        rightFrames[2] = spriteSheet.getSubimage(32 * 5, 32 * 6, width, height);

    }
}
