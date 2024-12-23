package Warriors91I.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class LoadSave {

    private static final String SPRITE_PATH = "images/outside_sprites.png";
    private static LoadSave instance;
    private Image image;

    private LoadSave() {
        load();
    }

    public static LoadSave getInstance() {
        if (instance == null) {
            instance = new LoadSave();
        }
        return instance;
    }

    private void load() {
        try {
            image = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream(SPRITE_PATH)
            );
        } catch (IOException e) {
            System.out.println("Error loading sprite: " + e.getMessage());
        }
    }

    public Image getMap() {
        return image;
    }


}
