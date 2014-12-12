package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class ArrowInfinity extends GlowEnchantment {
    ArrowInfinity() {
        super(51, "Infinity", 1, 1, EnchantmentTarget.BOW);
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
