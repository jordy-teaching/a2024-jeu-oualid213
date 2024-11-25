package Warriors91I;

import Doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class World {
    private static final String MAP_LEVEL0_PATH = "images/tiles.png";

    private static final String MAP_LEVEL1_PATH = "images/tiles1.png";
    private static final String MAP_LEVEL2_PATH = "images/tiles2.png";
    private static final String MAP_LEVEL3_PATH = "images/tiles3.png";
    private static final String MAP_LEVEL4_PATH = "images/tiles4.png";

    private Image level_0;
    private Image level_1;
    private Image level_2;
    private Image level_3;
    private Image level_4;


    public void load() {
        try {
            System.out.println();

            level_0 = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream(MAP_LEVEL0_PATH)
            );
            level_1 = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream(MAP_LEVEL1_PATH)
            );
            level_2 = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream(MAP_LEVEL2_PATH)
            );
            level_3 = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream(MAP_LEVEL3_PATH)
            );
            level_4 = ImageIO.read(
                    getClass().getClassLoader().getResourceAsStream(MAP_LEVEL4_PATH)
            );

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
           System.out.println("TOUJOURS EXECUTER");
        }
    }
    public void draw(Canvas canvas) {
        canvas.drawRectangle(0,0,10000,10000,Color.black);
        canvas.drawImage(level_0, 0, -0);
        canvas.drawImage(level_1, 800, -0);
        canvas.drawImage(level_2, 800*2, -0);
        canvas.drawImage(level_3, 800*3, -0);
        canvas.drawImage(level_4, 800*4, -0);


    }
}
