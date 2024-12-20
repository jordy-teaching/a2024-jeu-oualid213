package Warriors91I;

import Doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class  Background {

    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int BACKGROUND_WIDTH = 2000;
    private static final int BACKGROUND_HEIGHT = 2000;

    private Image tileImage;
    private int offsetX = 0;
    private int offsetY = 0;
    private Image skyImage;
    private Image cloudImage;
    private Image groundImage;


    public Background(String skyPath, String cloudPath, String groundPath) {
        try {
            tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(skyPath));
            cloudImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(cloudPath));
            groundImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(groundPath));
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des assets : " + e.getMessage());
        }
    }

    public void draw(Canvas canvas) {
        if (tileImage == null) {
            System.out.println("Image de fond introuvable");
            return;
        }

        for (int x = -GAME_WIDTH; x < GAME_WIDTH + BACKGROUND_WIDTH; x += GAME_WIDTH) {
            for (int y = -GAME_HEIGHT; y < GAME_HEIGHT + BACKGROUND_HEIGHT; y += GAME_HEIGHT) {
                if (y<GAME_HEIGHT){
                    canvas.drawImage(tileImage, x - offsetX, y - offsetY);
                }
            }
        }
    }
}

