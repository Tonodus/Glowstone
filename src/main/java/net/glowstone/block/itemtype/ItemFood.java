package net.glowstone.block.itemtype;

import net.glowstone.entity.GlowPlayer;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

public class ItemFood extends ItemType {
    @Override
    public void rightClickAir(GlowPlayer consumer, ItemStack holding) {
        //TODO check whether the player can actually eat (is hungry)
        if (consumer.getGameMode() != GameMode.CREATIVE && consumer.getGameMode() != GameMode.SPECTATOR && true) {
            consumer.tryConsuming(holding, 32);
        }
    }


    @Override
    public void consumed(GlowPlayer consumer, ItemStack item) {
        consumer.setSat
    }
}
