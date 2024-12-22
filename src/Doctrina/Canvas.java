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
    public void setFont(Font font){
        graphics.setFont(font);
    }

    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void drawFlippedImage(Image image, int x, int y, int width, int height) {
        saveState();
        translate(x + width, y);
        graphics.scale(-1, 1);
        graphics.drawImage(image, 0, 0, width, height, null); // Dessiner l'image inversée
        restoreState();
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

    public void translate(int x, int y) {
        graphics.translate(x, y);
    }

    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void drawShadowedRect(int x, int y, int width, int height, int arcWidth, int arcHeight, Paint fillPaint, Paint shadowPaint) {
        graphics.setPaint(shadowPaint);
        graphics.fillRoundRect(x + 5, y + 5, width, height, arcWidth, arcHeight);  // L'ombre est décalée

        graphics.setPaint(fillPaint);
        graphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    public void drawGradientRect(int x, int y, int width, int height, Color startColor, Color endColor) {
        GradientPaint gradient = new GradientPaint(x, y, startColor, x + width, y + height, endColor);
        graphics.setPaint(gradient);
        graphics.fillRect(x, y, width, height);
    }
    public void drawGlowingBorder(int x, int y, int width, int height, int glowThickness, Color glowColor) {
        saveState();

        graphics.setColor(glowColor);
        graphics.setStroke(new BasicStroke(1f));

        for (int i = glowThickness; i > 0; i--) {
            float alpha = (float) i / glowThickness;
            graphics.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), (int) (alpha * 255)));
            graphics.drawRoundRect(x - i, y - i, width + 2 * i, height + 2 * i, 20, 20);
        }

        restoreState();
    }


    public Graphics2D getGraphics() {
        return graphics;
    }
}
