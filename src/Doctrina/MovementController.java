package Doctrina;

import java.awt.event.KeyEvent;

public class MovementController extends Controller {

    private int upKey = KeyEvent.VK_UP;
    private int downKey = KeyEvent.VK_DOWN;
    private int leftKey = KeyEvent.VK_LEFT;
    private int rightKey = KeyEvent.VK_RIGHT;

    public MovementController() {
        findKey(upKey);
        findKey(downKey);
        findKey(leftKey);
        findKey(rightKey);
    }

    public void useWasdKeys() {
        setUpKey(KeyEvent.VK_W);
        setDownKey(KeyEvent.VK_S);
        setLeftKey(KeyEvent.VK_A);
        setRightKey(KeyEvent.VK_D);
    }

    public Direction getDirection() {
        if (isLeftPressed()) {
            return Direction.LEFT;
        }
        if (isRightPressed()) {
            return Direction.RIGHT;
        }
        if (isUpPressed()) {
            return Direction.UP;
        }

        if (isDownPressed()) {
            return Direction.DOWN;
        }
        return null;
    }

    public boolean isLeftPressed() {
        return isKeyPressed(leftKey);
    }

    public boolean isRightPressed() {
        return isKeyPressed(rightKey);
    }

    public boolean isUpPressed() {
        return isKeyPressed(upKey);
    }

    public boolean isDownPressed() {
        return isKeyPressed(downKey);
    }

    public boolean isMoving() {
        return isDownPressed() || isUpPressed()
                || isLeftPressed() || isRightPressed();
    }

    public void setDownKey(int keyCode) {
        unbindKey(downKey);
        findKey(keyCode);
        this.downKey = keyCode;
    }

    public void setUpKey(int keyCode) {
        unbindKey(upKey);
        findKey(keyCode);
        this.upKey = keyCode;
    }

    public void setLeftKey(int keyCode) {
        unbindKey(leftKey);
        findKey(keyCode);
        this.leftKey = keyCode;
    }

    public void setRightKey(int keyCode) {
        unbindKey(rightKey);
        findKey(keyCode);
        this.rightKey = keyCode;
    }
}
