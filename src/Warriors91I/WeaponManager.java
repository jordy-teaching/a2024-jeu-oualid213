package Warriors91I;

import Doctrina.Canvas;

import java.util.ArrayList;
import java.util.Iterator;

public class WeaponManager {
    private ArrayList<Weapon> weapons;

    public WeaponManager() {
        weapons = new ArrayList<>();
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public boolean isIntersect(Enemy enemy) {
        Iterator<Weapon> iterator = weapons.iterator();

        while (iterator.hasNext()) {
            Weapon weapon = iterator.next();
            if (weapon.intersectWith(enemy)) {
                weapon.isOutOfBounds(true);
                iterator.remove();
                return true;
            }
        }

        return false;
    }
    public int weaponDamage(){
        return 30;
    }

    public void updateWeapons() {
        Iterator<Weapon> iterator = weapons.iterator();
        while (iterator.hasNext()) {
            Weapon weapon = iterator.next();
            weapon.update();
            if (weapon.getIsOutOfBounds()) {
                iterator.remove();
            }
        }
    }

    public void drawWeapons(Canvas canvas) {
        for (Weapon weapon : weapons) {
            weapon.draw(canvas);
        }
    }
}
