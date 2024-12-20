package Doctrina;

public class Camera {

        private int x;
        private int y;
        private int viewWidth;
        private int viewHeight;

        public Camera(int viewWidth, int viewHeight) {
            this.viewWidth = viewWidth;
            this.viewHeight = viewHeight;
        }

        public void update(ControllableEntity player) {
            x = player.getX() - viewWidth / 2;
            y = player.getY() - viewHeight / 2;

            if (x < 0) x = 0;
            if (x > 800*7 - viewWidth) x = 800*7 - viewWidth;
            if (y > 600*2 - viewHeight) y = 600*2- viewHeight;
        }


        public int getX() { return x; }
        public int getY() { return y; }
}
