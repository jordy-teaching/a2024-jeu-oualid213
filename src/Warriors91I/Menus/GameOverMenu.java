package Warriors91I.Menus;

import Doctrina.Canvas;
import Warriors91I.Menu;
import Warriors91I.WarriorsGame;

import java.awt.*;

public class GameOverMenu extends Menu {

    private static final int MENU_WIDTH = 300;
    private static final int MENU_HEIGHT = 50;
    private static final int SPACING = 20;
    private WarriorsGame game;

    public GameOverMenu(WarriorsGame game) {
        this.game = game;
    }

    @Override
    public void addOptions() {
        options.add("Restart");
        options.add("Quit");
    }

    @Override
    public void executeOption() {
        switch (options.get(selectedIndex)) {
            case "Restart":
                game.restartGame();
                deactivate();
                break;
            case "Quit":
                System.exit(0);
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int startX = (800 - MENU_WIDTH) / 2;
        int startY = (680 - (options.size() * MENU_HEIGHT + (options.size() - 1) * SPACING)) / 2;


        canvas.drawGradientRect(startX-50, startY -130, 400, 280, new Color(0, 0, 0, 68), new Color(0, 0, 0, 68));

        canvas.setFont(new Font("Arial", Font.BOLD, 50));
        String gameOverText = "GAME OVER";
        int gameOverWidth = canvas.getGraphics().getFontMetrics().stringWidth(gameOverText);
        int gameOverX = (800 - gameOverWidth) / 2;
        int gameOverY = startY - 50;

        canvas.drawString(gameOverText, gameOverX, gameOverY, Color.RED);

        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);

            if (i == selectedIndex) {
                canvas.drawShadowedRect(
                        startX - 15,
                        startY + (i * (MENU_HEIGHT + SPACING)) - 15,
                        MENU_WIDTH + 30,
                        MENU_HEIGHT + 30,
                        20,
                        20,
                        new Color(213, 0, 0, 200),
                        new Color(0, 12, 28, 100)
                );
            }

            canvas.setFont(new Font("Arial", Font.BOLD, 20));
            int textWidth = canvas.getGraphics().getFontMetrics().stringWidth(option);
            int textX = startX + (MENU_WIDTH - textWidth) / 2;
            int textY = startY + (i * (MENU_HEIGHT + SPACING)) + MENU_HEIGHT / 2 + 7; // Vertically centered
            canvas.drawString(option, textX, textY, Color.white);
        }
    }
}