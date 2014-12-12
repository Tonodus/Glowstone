package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class DepthStrider extends GlowEnchantment {
    DepthStrider() {
        super(8, "Depth Strider", 3, 2, EnchantmentTarget.ARMOR_FEET);
    }

    @Override
    public int getMinRange(int modifier) {
        return modifier * 10;
    }

    @Override
    public int getMaxRange(int modifier) {
        return getMinRange(modifier) + 15;
    }
}
