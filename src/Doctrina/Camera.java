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
            if (y < 0) y = 0;
            // condition d'arrêt de la caméra
            if (x > 10000 - viewWidth) x = 10000 - viewWidth;
            if (y > 10000 - viewHeight) y = 10000 - viewHeight;
        }


        public int getX() { return x; }
        public int getY() { return y; }

}
