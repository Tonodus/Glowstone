package net.glowstone.constants.enchantments;

final class Smite extends WeaponDamage {
    Smite() {
        super(17, "Smite", 5);
    }

    @Override
    protected int getMinRange(int modifier) {
        return 5 + (modifier - 1) * 8;
    }
}
