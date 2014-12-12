package net.glowstone.constants.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;

final class Fortune extends LootBonus {
    Fortune() {
        super(35, "Fortune", EnchantmentTarget.TOOL, BASE_TOOLS, Group.DIG);
    }
}
