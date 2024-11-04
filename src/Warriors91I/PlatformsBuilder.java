package Warriors91I;

import Doctrina.Canvas;
import Doctrina.Physics;

public class PlatformsBuilder {

    private Platform[] platforms;
    private Platform[] airPlatform;
    private Physics physics;

    PlatformsBuilder(Physics physics) {
        platforms = new Platform[50];
        airPlatform = new Platform[4];
        this.physics = physics;
    }

    private void initializeDefaultPlatforms() {
        int startX = 0;
        int startY = 400;

        for (int i = 0; i < platforms.length; i++) {
            platforms[i] = new Platform(startX + (i * 32), startY);
        }
        for (int i = 0; i < airPlatform.length; i++) {
            airPlatform[i] = new Platform(200, 400 - (i * 32));
        }
    }

    public Platform[] createHorizontalPlatform(int startX, int startY, int length) {
        Platform[] horizontalPlatform = new Platform[length];
        for (int i = 0; i < length; i++) {
            horizontalPlatform[i] = new Platform(startX + (i * 32), startY);
        }
        return horizontalPlatform;
    }

    public Platform[] createVerticalPlatform(int startX, int startY, int height) {
        Platform[] verticalPlatform = new Platform[height];
        for (int i = 0; i < height; i++) {
            verticalPlatform[i] = new Platform(startX, startY - (i * 32));
        }
        return verticalPlatform;
    }

    public void physicsSetUp(Platform[] platforms, boolean inTheAir) {
         for (int i = 0 ; i<platforms.length; i++) {
                for (int j = 0; j < platforms[i].getBlockade().length-1; j++) {
                    if (!inTheAir){
                    physics.checkCollisions(platforms[i].getBlockade()[j]);
                    } else {
                        physics.checkCollisionsInTheAir(platforms[i].getBlockade()[j]);
                    }
                }
                physics.roofImpact(platforms[i].getBlockade()[platforms[i].getBlockade().length-1]);
            }

    }


    public void draw(Canvas canvas) {
        for (Platform platform : platforms) {
            platform.draw(canvas);
        }
        for (Platform platform : airPlatform) {
            platform.draw(canvas);
        }
    }

    public void drawPlatforms(Canvas canvas, Platform[] platformsToDraw) {
        for (Platform platform : platformsToDraw) {
            platform.draw(canvas);
        }
    }
}
