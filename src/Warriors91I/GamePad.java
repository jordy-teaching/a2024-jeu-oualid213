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
    private final int collect = KeyEvent.VK_E;
    private final int consume = KeyEvent.VK_C;




    public GamePad() {
        findKey(quitKey);
        findKey(fireKey);
        findKey(jumpKey);
        findKey(attackKey);
        findKey(shootKey);
        findKey(enter);
        findKey(collect);
        findKey(consume);

    }

    public boolean isQuitPressed() {
        return isKeyPressed(quitKey);
    }
    public boolean isWeaponPressed() {return isKeyPressed(shootKey);}
    public boolean isCollectPressed() {return isKeyPressed(collect);}

    public boolean isConsumePressed() {return isKeyPressed(consume);}

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
