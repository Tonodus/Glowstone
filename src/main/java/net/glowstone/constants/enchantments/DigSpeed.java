package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class DigSpeed extends GlowEnchantment {
    DigSpeed() {
        super(32, "Efficiency", 5, 10, EnchantmentTarget.TOOL, DIGGING_TOOLS);
    }

    @Override
    public int getMinRange(int modifier) {
        return 1 + 10 * (modifier - 1);
    }

    @Override
    public int getMaxRange(int modifier) {
        return super.getMinRange(modifier) + 50;
    }
}
