package Warriors91I;

import Doctrina.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {
    private final int quitKey = KeyEvent.VK_Q;
    private final int fireKey = KeyEvent.VK_SPACE;

    private final int jumpKey = KeyEvent.VK_SPACE;

    public GamePad() {
        bindKey(quitKey);
        bindKey(fireKey);
        bindKey(jumpKey);
    }

    public boolean isQuitPressed() {
        return isKeyPressed(quitKey);
    }

    public boolean isJumpPressed(){
        return isKeyPressed(jumpKey);
    }

    public boolean isFirePressed() {
        return isKeyPressed(fireKey);
    }
}
