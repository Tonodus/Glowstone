package net.glowstone.inventory;

import net.glowstone.entity.GlowPlayer;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class GlowEnchantingInventory extends GlowInventory implements EnchantingInventory {
    private static final int ITEM_SLOT = 0;
    private static final int LAPIS_SLOT = 1;

    private final Location location;

    public GlowEnchantingInventory(Location location, InventoryHolder holder) {
        super(holder, InventoryType.ENCHANTING);

        slotTypes[ITEM_SLOT] = InventoryType.SlotType.CRAFTING;
        slotTypes[LAPIS_SLOT] = InventoryType.SlotType.CRAFTING;
        this.location = location;
    }

    @Override
    public void setItem(int index, ItemStack item) {
        super.setItem(index, item);

        for (HumanEntity viewer : getViewers()) {
            if (viewer instanceof GlowPlayer) {
                ((GlowPlayer) viewer).getEnchantmentManager().updatePossibilities();
            }
        }
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public int getRawSlots() {
        return 0;
    }

    @Override
    public void setItem(ItemStack item) {
        setItem(ITEM_SLOT, item);
    }

    @Override
    public ItemStack getItem() {
        return getItem(ITEM_SLOT);
    }

    @Override
    public void setResource(ItemStack item) {
        setItem(LAPIS_SLOT, item);
    }

    @Override
    public ItemStack getResource() {
        return getItem(LAPIS_SLOT);
    }
}
