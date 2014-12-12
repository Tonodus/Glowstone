package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class SilkTouch extends GlowEnchantment {
    SilkTouch() {
        super(33, "Silk Touch", 1, 1, EnchantmentTarget.TOOL, DIGGING_TOOLS, Group.DIG);
    }

    @Override
    public int getMinRange(int modifier) {
        return 15;
    }

    @Override
    public int getMaxRange(int modifier) {
        return super.getMinRange(modifier) + 50;
    }
}
