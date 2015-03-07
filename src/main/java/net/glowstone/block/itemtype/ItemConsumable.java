package net.glowstone.block.itemtype;

import net.glowstone.entity.GlowPlayer;
import org.bukkit.inventory.ItemStack;

public abstract class ItemConsumable extends ItemType {
    private final int timeToConsume;

    public ItemConsumable(int timeToConsume) {
        this.timeToConsume = timeToConsume;
    }


    @Override
    public abstract void consumed(GlowPlayer consumer, ItemStack item);
}
