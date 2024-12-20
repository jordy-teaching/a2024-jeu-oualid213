package Warriors91I;

public class Ghost extends Enemy {
    public Ghost(int x, int y) {
        super(x, y);
        speed = 4;
    }

    /*
    * we can*/


        @Override
        protected void loadAnimationFrames() {
        leftFrames[0] = spriteSheet.getSubimage(32 * 9, 32 * 1, width, height);
        leftFrames[1] = spriteSheet.getSubimage(32 * 10, 32 * 1, width, height);
        leftFrames[2] = spriteSheet.getSubimage(32 * 11, 32 * 1, width, height);

        rightFrames[0] = spriteSheet.getSubimage(32 * 9, 32 * 2, width, height);
        rightFrames[1] = spriteSheet.getSubimage(32 * 10, 32 * 2, width, height);
        rightFrames[2] = spriteSheet.getSubimage(32 * 11, 32 * 2, width, height);
    }
}
