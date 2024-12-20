package Warriors91I;

import Doctrina.Canvas;
import java.awt.Color;

import java.util.ArrayList;

public class Menu {
    private ArrayList<String> options;
    private int selectedIndex;
    private boolean active;
    private long lastInputTime;
    private static final int SPACING = 10;  // Espacement entre les options

    private static final long COOLDOWN_TIME = 200;
    private static final int MENU_WIDTH = 300; // Largeur du menu
    private static final int MENU_HEIGHT = 40; // Hauteur de chaque option

    public Menu() {
        options = new ArrayList<>();
        selectedIndex = 0;
        active = true;
        lastInputTime = System.currentTimeMillis();
    }

    public void addOption(String option) {
        options.add(option);
    }

    public void handleInput(GamePad gamePad) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastInputTime < COOLDOWN_TIME) {
            return;
        }

        if (gamePad.isDownPressed()) {
            selectedIndex = (selectedIndex + 1) % options.size();
            lastInputTime = currentTime;
        } else if (gamePad.isUpPressed()) {
            selectedIndex = (selectedIndex - 1 + options.size()) % options.size();
            lastInputTime = currentTime;
        } else if (gamePad.isSelectPressed()) {
            executeOption();
            lastInputTime = currentTime;
        }
    }

    private void executeOption() {
        String option = options.get(selectedIndex);
        switch (option) {
            case "Start Game":
                active = false;
                break;
            case "Options":
                System.out.println("Options menu not implemented yet!");
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }

    public void draw(Canvas canvas) {
        int startX = (800 - MENU_WIDTH) / 2;
        int startY = (600 - (options.size() * MENU_HEIGHT + (options.size() - 1) * SPACING)) / 2;

        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i);

            Color backgroundColor = (i == selectedIndex)
                    ? new Color(255, 0, 0)
                    : new Color(255, 255, 255);

            if (i == selectedIndex) {
                canvas.drawRectangle(startX - 10, startY + (i * (MENU_HEIGHT + SPACING)) - 10, MENU_WIDTH + 20, MENU_HEIGHT + 20, new Color(50, 50, 50, 128));
            }

            if (i == selectedIndex) {
                canvas.drawShadowedRect(startX - 5, startY + (i * MENU_HEIGHT + SPACING) - 5, MENU_WIDTH + 10, MENU_HEIGHT + 10, 15, 15, new Color(50, 50, 50, 128), new Color(255, 255, 255, 100));
            }

            canvas.fillRoundRect(startX, startY + (i * (MENU_HEIGHT + SPACING)), MENU_WIDTH, MENU_HEIGHT, 15, 15, backgroundColor);

            Color textColor = (i == selectedIndex) ? Color.white : new Color(0, 0, 0);
            canvas.drawString(option, startX + 10, startY + (i * (MENU_HEIGHT + SPACING)) + 25, textColor);
        }
    }

    public boolean isActive() {
        return active;
    }
}
