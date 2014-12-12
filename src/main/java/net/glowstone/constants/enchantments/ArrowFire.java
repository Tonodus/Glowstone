package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class ArrowFire extends GlowEnchantment {
    ArrowFire() {
        super(50, "Flame", 1, 2, EnchantmentTarget.BOW);
    }

    @Override
    public int getMinRange(int modifier) {
        return 20;
    }

    @Override
    public int getMaxRange(int modifier) {
        return 50;
    }
}
