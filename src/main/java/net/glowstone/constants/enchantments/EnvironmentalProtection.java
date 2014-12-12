package net.glowstone.constants.enchantments;

final class EnvironmentalProtection extends Protection {
    EnvironmentalProtection() {
        super(0, "Protection", 10);
    }

    @Override
    protected int getMinRange(int modifier) {
        return 1 + (modifier - 1) * 11;
    }

    @Override
    protected int getMaxRange(int modifier) {
        return getMinRange(modifier) + 20;
    }
}
