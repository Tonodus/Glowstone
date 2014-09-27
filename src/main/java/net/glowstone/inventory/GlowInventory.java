package net.glowstone.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import java.util.*;

/**
 * A class which stores items in a list.
 */
public class GlowInventory extends GlowBaseInventory {

    /**
     * This inventory's slots.
     */
    private List<GlowInventorySlot> slots;

    public GlowInventory(InventoryHolder owner, InventoryType type) {
        this(owner, type, type.getDefaultSize(), type.getDefaultTitle());
    }

    public GlowInventory(InventoryHolder owner, InventoryType type, int size) {
        this(owner, type, size, type.getDefaultTitle());
    }

    public GlowInventory(InventoryHolder owner, InventoryType type, int size, String title) {
        initialize(owner, type, title, new HashSet<HumanEntity>());

        this.slots = GlowInventorySlot.createList(size);
    }

    @Override
    public GlowInventorySlot getSlot(int slot) {
        return slots.get(slot);
    }

    @Override
    public int getSize() {
        return slots.size();
    }

    @Override
    public Iterator<GlowInventorySlot> slotIterator() {
        return slots.iterator();
    }
}
