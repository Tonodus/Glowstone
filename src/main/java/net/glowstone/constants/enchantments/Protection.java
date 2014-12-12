package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

abstract class Protection extends GlowEnchantment {
    protected Protection(int id, String name, int weight) {
        this(id, name, weight, EnchantmentTarget.ARMOR);
    }

    protected Protection(int id, String name, int weight, EnchantmentTarget target) {
        super(id, name, 4, weight, target, Group.PROTECT);
    }
}
