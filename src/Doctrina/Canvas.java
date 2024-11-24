package Doctrina;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Canvas {

    private Graphics2D graphics;

    public Canvas(Graphics2D graphics) {
        this.graphics = graphics;
    }

    public void drawString(String text, int x, int y, Paint paint) {
        graphics.setPaint(paint);
        graphics.drawString(text, x, y);
    }

    public void drawCircle(int x, int y, int radius, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillOval(x, y, radius * 2, radius * 2);
    }

    public void drawRectangle(StaticEntity entity, Paint paint) {
        drawRectangle(entity.x, entity.y, entity.width, entity.height, paint);
    }

    public void drawRectangle(int x, int y, int width, int height, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect(x, y, width, height);
    }
    private AffineTransform originalTransform;

    public void saveState() {
        originalTransform = graphics.getTransform();
    }

    public void restoreState() {
        if (originalTransform != null) {
            graphics.setTransform(originalTransform);
        }
    }


    public void drawImage(Image image, int x, int y, int width, int height) {
        graphics.drawImage(image, x, y, width, height, null);
    }
    public void drawImage(Image image, int x, int y) {
        graphics.drawImage(image, x, y, null);
    }
    // Méthodes de transformation
    public void translate(int x, int y) {
        graphics.translate(x, y);
    }

}
