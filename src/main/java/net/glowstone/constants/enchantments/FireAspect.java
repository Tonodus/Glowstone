package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class FireAspect extends GlowEnchantment {
    FireAspect() {
        super(20, "Fire Aspect", 2, 2, EnchantmentTarget.WEAPON);
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
