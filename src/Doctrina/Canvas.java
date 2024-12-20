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

    // Méthodes de transformation
    public void translate(int x, int y) {
        graphics.translate(x, y);
    }

    // Nouvelle méthode pour dessiner un rectangle avec des coins arrondis
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    // Méthode pour dessiner une bordure avec ombre portée
    public void drawShadowedRect(int x, int y, int width, int height, int arcWidth, int arcHeight, Paint fillPaint, Paint shadowPaint) {
        // Dessin de l'ombre
        graphics.setPaint(shadowPaint);
        graphics.fillRoundRect(x + 5, y + 5, width, height, arcWidth, arcHeight);  // L'ombre est décalée

        // Dessin du rectangle avec la couleur de remplissage
        graphics.setPaint(fillPaint);
        graphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    // Nouvelle méthode pour dessiner un dégradé
    public void drawGradientRect(int x, int y, int width, int height, Color startColor, Color endColor) {
        GradientPaint gradient = new GradientPaint(x, y, startColor, x + width, y + height, endColor);
        graphics.setPaint(gradient);
        graphics.fillRect(x, y, width, height);
    }
}
