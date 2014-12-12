package net.glowstone.constants.enchantments;

final class BlastProtection extends Protection {
    BlastProtection() {
        super(3, "Blast Protection", 2);
    }

    @Override
    protected int getMinRange(int modifier) {
        return 5 + (modifier - 1) * 8;
    }

    @Override
    protected int getMaxRange(int modifier) {
        return getMinRange(modifier) + 12;
    }
}
