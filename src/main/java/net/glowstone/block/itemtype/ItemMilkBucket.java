package net.glowstone.block.itemtype;

import net.glowstone.entity.GlowPlayer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemMilkBucket extends ItemType {
    @Override
    public void rightClickAir(GlowPlayer player, ItemStack holding) {
        player.tryConsuming(holding, 32);
    }

    @Override
    public boolean consumed(GlowPlayer consumer, ItemStack item) {
        consumer.removeAllPotionEffects();

        if (consumer.getGameMode() != GameMode.CREATIVE) {
            consumer.setItemInHand(new ItemStack(Material.BUCKET));
        }

        return false;
    }
}
