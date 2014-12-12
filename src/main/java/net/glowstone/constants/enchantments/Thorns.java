package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class Thorns extends GlowEnchantment {
    Thorns() {
        super(7, "Thorns", 3, 1, EnchantmentTarget.ARMOR_TORSO, new MatcherAdapter(EnchantmentTarget.ARMOR));
    }

    @Override
    public int getMinRange(int modifier) {
        return 10 + 20 * (modifier - 1);
    }

    @Override
    public int getMaxRange(int modifier) {
        return super.getMinRange(modifier) + 50;
    }
}
