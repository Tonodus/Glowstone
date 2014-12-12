package net.glowstone.constants.enchantments;

final class FireProtection extends Protection {
    FireProtection() {
        super(1, "Fire Protection", 5);
    }

    @Override
    protected int getMinRange(int modifier) {
        return 10 + (modifier - 1) * 8;
    }

    @Override
    protected int getMaxRange(int modifier) {
        return getMinRange(modifier) + 12;
    }
}
