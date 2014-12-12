package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class Lure extends GlowEnchantment {
    Lure() {
        super(62, "Lure", 3, 2, EnchantmentTarget.FISHING_ROD);
    }

    @Override
    public int getMinRange(int modifier) {
        return 15 + (modifier - 1) * 9;
    }

    @Override
    public int getMaxRange(int modifier) {
        return super.getMinRange(modifier) + 50;
    }
}
