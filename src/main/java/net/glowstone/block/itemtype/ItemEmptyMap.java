package net.glowstone.block.itemtype;

import net.glowstone.EventFactory;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

public class ItemEmptyMap extends ItemType {
    public void rightClickAir(GlowPlayer player, ItemStack holding) {
        //Create the new map
        MapView newMap = Bukkit.createMap(player.getLocation().getWorld());
        newMap.setCenterX(player.getLocation().getBlockX());
        newMap.setCenterZ(player.getLocation().getBlockZ());
        newMap.setScale(MapView.Scale.NORMAL);

        //Call event
        MapInitializeEvent event = new MapInitializeEvent(newMap);
        EventFactory.callEvent(event);

        //add empty map
        int amount = holding.getAmount() - 1;
        if (amount > 0) {
            player.setItemInHand(new ItemStack(Material.MAP));
            player.getInventory().addItem(new ItemStack(Material.EMPTY_MAP, amount));
        }

        //Display new map
        holding.setAmount(1);
        holding.setType(Material.MAP);
        holding.setDurability(newMap.getId());
    }
}
