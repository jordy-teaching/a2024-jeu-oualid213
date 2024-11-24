package Warriors91I;

import Doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class World {
    private static final String MAP_PATH = "images/tiles.png";

    private Image background;

    public void load() {
        try {
            System.out.println();

            background = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream(MAP_PATH)
            );

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
           System.out.println("TOUJOURS EXECUTER");
        }
    }
    public void draw(Canvas canvas) {
        canvas.drawRectangle(0,0,1000,1000,Color.black);
        canvas.drawImage(background, 0, -0);
        canvas.drawImage(background, 800, -0);
        canvas.drawImage(background, 800*2, -0);

    }
}
