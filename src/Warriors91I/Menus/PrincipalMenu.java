package Warriors91I.Menus;

import Doctrina.Canvas;
import Warriors91I.Menu;

import java.awt.Color;

public final class PrincipalMenu extends Menu {

    private static final int SPACING = 15;
    private static final int MENU_WIDTH = 350;
    private static final int MENU_HEIGHT = 50;

    public PrincipalMenu() {
        super();
        selectedIndex = 0;
        active = true;
    }

    @Override
    public void addOptions() {
        options.add("Start Game");
        options.add("Options");
        options.add("Exit");

    }

    @Override
    public void executeOption() {
        String option = options.get(selectedIndex);
        switch (option) {
            case "Start Game":
                active = false;
                break;
            case "Options":
                System.out.println("Options menu not implemented yet, one day");
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int startX = (800 - MENU_WIDTH) / 2;
        int startY = (600 - (options.size() * MENU_HEIGHT + (options.size() - 1) * SPACING)) / 2;

        canvas.drawGradientRect(0, 0, 800, 600, new Color(16, 16, 68), new Color(5, 5, 30));

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
                        new Color(0, 36, 101, 200),
                        new Color(0, 70, 134, 100)
                );
            }

            canvas.drawGradientRect(
                    startX,
                    startY + (i * (MENU_HEIGHT + SPACING)),
                    MENU_WIDTH,
                    MENU_HEIGHT,
                    new Color(0, 100, 200),
                    new Color(0, 150, 255)
            );

            Color textColor = (i == selectedIndex) ? Color.white : new Color(200, 200, 255);
            canvas.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
            int textWidth = canvas.getGraphics().getFontMetrics().stringWidth(option);
            int textX = startX + (MENU_WIDTH - textWidth) / 2;
            int textY = startY + (i * (MENU_HEIGHT + SPACING)) + MENU_HEIGHT / 2 + 7; // Vertically centered
            canvas.drawString(option, textX, textY, textColor);
        }

    }

    public boolean isActive() {
        return active;
    }
}
