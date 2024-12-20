package Warriors91I;

import Doctrina.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {
    private final int quitKey = KeyEvent.VK_Q;
    private final int fireKey = KeyEvent.VK_SPACE;
    private final int jumpKey = KeyEvent.VK_SPACE;
    private final int attackKey = KeyEvent.VK_S;
    private final int shootKey = KeyEvent.VK_D;
    private final int enter = KeyEvent.VK_ENTER;



    public GamePad() {
        findKey(quitKey);
        findKey(fireKey);
        findKey(jumpKey);
        findKey(attackKey);
        findKey(shootKey);
        findKey(enter);

    }

    public boolean isQuitPressed() {
        return isKeyPressed(quitKey);
    }
    public boolean isWeaponPressed() {return isKeyPressed(shootKey);}


    public boolean isJumpPressed(){
        return isKeyPressed(jumpKey);
    }
    public boolean isAttackPressed(){return isKeyPressed(attackKey);}

    public boolean isFirePressed() {
        return isKeyPressed(fireKey);
    }

    public boolean isSelectPressed() {return isKeyPressed(enter);
    }
}
