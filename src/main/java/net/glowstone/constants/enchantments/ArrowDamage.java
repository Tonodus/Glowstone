package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class ArrowDamage extends GlowEnchantment {
    ArrowDamage() {
        super(48, "Power", 5, 10, EnchantmentTarget.BOW);
    }

    @Override
    public int getMinRange(int modifier) {
        return 1 + (modifier - 1) * 10;
    }

    @Override
    public int getMaxRange(int modifier) {
        return getMinRange(modifier) + 15;
    }
}
