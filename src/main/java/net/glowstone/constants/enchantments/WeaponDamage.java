package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

abstract class WeaponDamage extends GlowEnchantment {
    protected WeaponDamage(int id, String name, int weight) {
        super(id, name, 5, weight, EnchantmentTarget.WEAPON, SWORD_OR_AXE, Group.ATTACK);
    }

    @Override
    protected int getMaxRange(int modifier) {
        return getMinRange(modifier) + 20;
    }
}
