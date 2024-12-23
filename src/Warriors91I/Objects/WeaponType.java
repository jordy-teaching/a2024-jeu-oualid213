package Warriors91I.Objects;

public enum WeaponType {
    GUN(10, "Gun"),
    BOW(15, "Bow"),
    FLAME_THROWER(50, "Flame Thrower");

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

