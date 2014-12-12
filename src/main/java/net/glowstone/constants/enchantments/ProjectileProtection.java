package net.glowstone.constants.enchantments;

final class ProjectileProtection extends Protection {
    ProjectileProtection() {
        super(4, "Projectile Protection", 5);
    }

    @Override
    protected int getMinRange(int modifier) {
        return 3 + (modifier - 1) * 6;
    }

    @Override
    protected int getMaxRange(int modifier) {
        return getMinRange(modifier) + 15;
    }
}
