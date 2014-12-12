package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class ArrowKnockback extends GlowEnchantment {
    ArrowKnockback() {
        super(49, "Punch", 2, 2, EnchantmentTarget.BOW);
    }

    @Override
    public int getMinRange(int modifier) {
        return 12 + (modifier - 1) * 20;
    }

    @Override
    public int getMaxRange(int modifier) {
        return getMinRange(modifier) + 25;
    }
}
