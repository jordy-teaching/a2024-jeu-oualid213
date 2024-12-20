package Warriors91I;

public enum WeaponType {
    GUN(10, "Gun"),
    BOW(15, "Bow"),
    FLAME_THROWER(25, "Flame Thrower");

    private final int damage;
    private final String weaponLink;

    WeaponType(int damage, String weaponLink) {
        this.damage = damage;
        this.weaponLink = weaponLink;
    }

    public int getDamage() {
        return damage;
    }

    public String getWeaponLink() {
        return weaponLink;
    }
}

