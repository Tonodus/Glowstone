package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class Oxygen extends GlowEnchantment {
    Oxygen() {
        super(5, "Respiration", 3, 2, EnchantmentTarget.ARMOR_HEAD);
    }

    @Override
    public int getMinRange(int modifier) {
        return 10 * modifier;
    }

    @Override
    public int getMaxRange(int modifier) {
        return getMinRange(modifier) + 30;
    }
}
