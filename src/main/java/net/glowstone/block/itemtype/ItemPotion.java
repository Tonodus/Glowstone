package net.glowstone.block.itemtype;

import net.glowstone.entity.GlowPlayer;
import net.glowstone.net.message.play.entity.AnimateEntityMessage;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

public class ItemPotion extends ItemType {
    @Override
    public void rightClickAir(GlowPlayer clicker, ItemStack holding) {
        Logger.getAnonymousLogger().info("rc air");

        for (GlowPlayer player : clicker.getWorld().getRawPlayers()) {
            if (player.canSee(clicker)) {
                player.getSession().send(new AnimateEntityMessage(clicker.getEntityId(), AnimateEntityMessage.OUT_EAT_FOOD));
            }
        }
    }
}
