package Warriors91I;

import Doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World {

    private static final String BACKGROUND_PATH = "images/_bg.png";
    private static final String[] MAP_LEVEL_PATHS = {
            "images/tiles.png",
            "images/tiles1.png",
            "images/tiles2.png",
            "images/tiles3.png",
            "images/tiles4.png",
            "images/tiles5.png",
            "images/tiles7.png",
            "images/tiles8.png",
            "images/tiles9.png",
            "images/tiles10.png"
    };

    private Background background;
    private final List<Image> levels = new ArrayList<>();

    public void load() {
        try {
            background = new Background("images/_bg.png");

            for (String path : MAP_LEVEL_PATHS) {
                levels.add(loadImage(path));
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des images : " + e.getMessage());
        } finally {
            System.out.println("Chargement terminé");
        }
    }

    private Image loadImage(String path) throws IOException {
        return ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
    }
    public void update() {
        if (background != null) {
            background.update(2, 0);
        }
    }

    public void draw(Canvas canvas) {
        background.draw(canvas);
        int xOffset = 0;
        for (int i = 0; i < 6 && i < levels.size(); i++) {
            canvas.drawImage(levels.get(i), xOffset, 0);
            xOffset += 800;
        }

        xOffset = 0;
        for (int i = 6; i < levels.size(); i++) {
            canvas.drawImage(levels.get(i), xOffset, 600);
            xOffset += 800;
        }
    }
}
