package Warriors91I;

import Doctrina.Canvas;
import Doctrina.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Shield extends StaticEntity {


    private int heal;
    private BufferedImage image;
    private Image bottle;
    private int x;
    private int y;

    public Shield( int x, int y) {
        teleport(x, y);
        setDimension(100, 100);
        heal = 50;
        this.x =x;
        this.y = y;

        this.heal = heal;
        load();
    }

    public int getHeal() {
        return this.heal;
    }

    private void load() {
        loadAnimationFrames();

        loadSpriteSheet();
    }

    private void loadAnimationFrames() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/Shield.png"));
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du sprite sheet : " + e.getMessage());
        }
    }

    private void loadSpriteSheet() {
        bottle= image.getSubimage(0, 0, 32, 32);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawImage(bottle,x,y);
    }
}
