package Warriors91I;

import Doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World {

    private static final String BACKGROUND_PATH = "images/_bg.png";
    private static final String SKY_PATH = "images/sky.png";
    private static final String LEVEL9_PATH = "images/Tiles9.2.png";
    private static final String LEVEL8_PATH = "images/Tiles8.2.png";
    private static final String[] MAP_LEVEL_PATHS = {
            "images/tiles.png",
            "images/tiles1.png",
            "images/tiles2.png",
            "images/tiles3.png",
            "images/tiles4.png",
            "images/tiles5.png",
            "images/tiles6.png",
            "images/tiles8.png",
            "images/tiles9.png",
            "images/tiles10.png",
            "images/tiles7.png",
            "images/tiles12.png",
            "images/tiles13.png",
            "images/tiles14.png",
            "images/tiles15.png",
            "images/tiles16.png",
            "images/tiles17.png",
            "images/tiles18.png",
            "images/tiles19.png",
            "images/tiles20.png",
            "images/tiles21.png"
    };

    private Image sky;
    private Image level9;
    private Image level8;
    private Image background;
    private final List<Image> levels = new ArrayList<>();

    public void load() {
        try {
            sky = loadImage(SKY_PATH);
            background = loadImage(BACKGROUND_PATH);
            level9 = loadImage(LEVEL9_PATH);
            level8 = loadImage(LEVEL8_PATH);

            for (String path : MAP_LEVEL_PATHS) {
                levels.add(loadImage(path));
            }
        } catch (Exception e) {
            System.out.println("Error loading images: " + e.getMessage());
        } finally {
            System.out.println("Loading completed");
        }
    }

    private Image loadImage(String path) throws IOException {
        return ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
    }

    public void draw(Canvas canvas) {
        drawRepeatedImage(canvas, sky, 800 * 7, -600);
        drawRepeatedImage(canvas, sky, 800 * 7, -300);
        drawRepeatedImage(canvas, sky, 800 * 7, -0);
        drawRepeatedImage(canvas, sky, 800 * 7, 300);

        drawRepeatedImage(canvas, background, 1920 * 5, 600);

        drawLevelSet(canvas, 0, 7, 0, 800);
        drawLevelSet(canvas, 14, levels.size(), -600, 800);
        drawLevelSetWithOverrides(canvas, 7, 14, 600, 800);
    }

    private void drawRepeatedImage(Canvas canvas, Image image, int width, int yPosition) {
        for (int x = 0; x < width; x += 800) {
            canvas.drawImage(image, x, yPosition);
        }
    }

    private void drawLevelSet(Canvas canvas, int startIndex, int endIndex, int yPosition, int xIncrement) {
        int xOffset = 0;
        for (int i = startIndex; i < endIndex && i < levels.size(); i++) {
            canvas.drawImage(levels.get(i), xOffset, yPosition);
            xOffset += xIncrement;
        }
    }

    private void drawLevelSetWithOverrides(Canvas canvas, int startIndex, int endIndex, int yPosition, int xIncrement) {
        int xOffset = 0;
        for (int i = startIndex; i < endIndex; i++) {
            if (i == 8) {
                canvas.drawImage(level9, xOffset, yPosition);
            } else if (i == 7) {
                canvas.drawImage(level8, xOffset, yPosition);
            }
                canvas.drawImage(levels.get(i), xOffset, yPosition);


            xOffset += xIncrement;
        }
    }
}
