package Warriors91I;

import Doctrina.Canvas;
import java.awt.*;
import java.util.ArrayList;

public abstract class Menu {
    protected boolean active;
    private long lastInputTime;
    protected ArrayList<String> options;
    protected int selectedIndex;
    private static final int SPACING = 15;
    private static final long COOLDOWN_TIME = 200;
    private static final int MENU_WIDTH = 350;
    private static final int MENU_HEIGHT = 50;

    public Menu() {
        options = new ArrayList<>();
        selectedIndex = 0;
        active = false;
        addOptions();
    }

    abstract public void addOptions();
    abstract public void executeOption();

    protected void handleInput(GamePad gamePad, WarriorsGame warriorsGame) {
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

    public void draw(Canvas canvas) {
        int canvasWidth = 800;
        int canvasHeight = 600;
        int windowX = (canvasWidth - MENU_WIDTH) / 2;
        int windowY = (canvasHeight - (MENU_HEIGHT * options.size() + SPACING * (options.size() - 1))) / 2;

        Color backgroundColor = new Color(0, 0, 0, 180);
        canvas.drawRectangle(0, 0, canvasWidth, canvasHeight, backgroundColor);

        for (int i = 0; i < options.size(); i++) {
            int optionY = windowY + i * (MENU_HEIGHT + SPACING);
            drawOption(canvas, options.get(i), windowX, optionY, i == selectedIndex);
        }
    }

    private void drawOption(Canvas canvas, String text, int x, int y, boolean isSelected) {
        Color buttonColor = isSelected ? new Color(100, 200, 100) : new Color(200, 200, 200);
        Color textColor = isSelected ? new Color(0, 0, 0) : new Color(50, 50, 50);

        canvas.drawRectangle(x, y, MENU_WIDTH, MENU_HEIGHT, buttonColor);

        Font font = new Font("Arial", Font.BOLD, 20);
        canvas.setFont(font);
        int textWidth = canvas.getGraphics().getFontMetrics(font).stringWidth(text);
        int textX = x + (MENU_WIDTH - textWidth) / 2;
        int textY = y + (MENU_HEIGHT + font.getSize()) / 2 - 5;
        canvas.drawString(text, textX, textY, textColor);
    }

    public void activate() {
        this.active = true;
        selectedIndex = 0;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }
}
