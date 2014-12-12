package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class Knockback extends GlowEnchantment {
    Knockback() {
        super(19, "Knockback", 2, 5, EnchantmentTarget.WEAPON);
    }

    @Override
    public int getMinRange(int modifier) {
        return 5 + 20 * (modifier - 1);
    }

    @Override
    public int getMaxRange(int modifier) {
        return super.getMinRange(modifier) + 50;
    }
}
