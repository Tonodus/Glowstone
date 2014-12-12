package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class FallProtection extends Protection {
    FallProtection() {
        super(2, "Feather Falling", 5, EnchantmentTarget.ARMOR_FEET);
    }

    @Override
    protected int getMinRange(int modifier) {
        return 5 + (modifier - 1) * 6;
    }

    @Override
    protected int getMaxRange(int modifier) {
        return getMinRange(modifier) + 10;
    }
}
