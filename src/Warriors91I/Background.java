package Warriors91I;

import Doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background {

    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    private static final int BACKGROUND_WIDTH = 10000;
    private static final int BACKGROUND_HEIGHT = 10000;

    private Image tileImage;
    private int offsetX = 0;
    private int offsetY = 0;

    public Background(String imagePath) {
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath));
            tileImage = originalImage.getScaledInstance(GAME_WIDTH, GAME_HEIGHT, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }
    }


    public void update(int deltaX, int deltaY) {
        offsetX += deltaX;
        offsetY += deltaY;

        // Assurer que le décalage reste dans les limites du background
        offsetX = (offsetX + BACKGROUND_WIDTH) % BACKGROUND_WIDTH;
        offsetY = (offsetY + BACKGROUND_HEIGHT) % BACKGROUND_HEIGHT;
    }


    public void draw(Canvas canvas) {
        if (tileImage == null) {
            System.out.println("Image de fond introuvable");
            return;
        }

        for (int x = -GAME_WIDTH; x < GAME_WIDTH + BACKGROUND_WIDTH; x += GAME_WIDTH) {
            for (int y = -GAME_HEIGHT; y < GAME_HEIGHT + BACKGROUND_HEIGHT; y += GAME_HEIGHT) {
                canvas.drawImage(tileImage, x - offsetX, y - offsetY);
            }
        }
    }
}
