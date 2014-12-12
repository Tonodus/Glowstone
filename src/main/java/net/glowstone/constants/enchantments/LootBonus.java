package net.glowstone.constants.enchantments;

import net.glowstone.inventory.MaterialMatcher;
import org.bukkit.enchantments.EnchantmentTarget;

abstract class LootBonus extends GlowEnchantment {
    protected LootBonus(int id, String name, EnchantmentTarget target) {
        super(id, name, 3, 2, target);
    }

    protected LootBonus(int id, String name, EnchantmentTarget target, MaterialMatcher matcher, Group group) {
        super(id, name, 3, 2, target, matcher, group);
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
