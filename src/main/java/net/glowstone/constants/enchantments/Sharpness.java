package net.glowstone.constants.enchantments;

final class Sharpness extends WeaponDamage {
    Sharpness() {
        super(16, "Sharpness", 10);
    }

    @Override
    protected int getMinRange(int modifier) {
        return 1 + (modifier - 1) * 11;
    }
}
