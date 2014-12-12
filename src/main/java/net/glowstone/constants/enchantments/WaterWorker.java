package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class WaterWorker extends GlowEnchantment {
    WaterWorker() {
        super(6, "Aqua Affinity", 1, 2, EnchantmentTarget.ARMOR_HEAD);
    }

    @Override
    public int getMinRange(int modifier) {
        return 1;
    }

    @Override
    public int getMaxRange(int modifier) {
        return 41;
    }
}
