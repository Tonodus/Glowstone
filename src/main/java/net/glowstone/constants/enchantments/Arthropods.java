package net.glowstone.constants.enchantments;

final class Arthropods extends WeaponDamage {
    Arthropods() {
        super(18, "Bane of Arthropods", 5);
    }

    @Override
    protected int getMinRange(int modifier) {
        return 5 + (modifier - 1) * 8;
    }
}
