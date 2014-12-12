package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

class Durability extends GlowEnchantment {
    Durability() {
        super(34, "Unbreaking", 3, 5, EnchantmentTarget.TOOL, ALL_THINGS);
    }

    @Override
    public int getMinRange(int modifier) {
        return 5 + (modifier - 1) * 8;
    }

    @Override
    public int getMaxRange(int modifier) {
        return super.getMaxRange(modifier) + 50;
    }
}
