package net.glowstone.block.itemtype;

import net.glowstone.entity.GlowHumanEntity;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.inventory.ItemStack;

public class ItemMap extends ItemType {
    public void onHide(GlowHumanEntity holder, ItemStack me, ItemStack nowHolding) {
        if (holder instanceof GlowPlayer) {
            GlowPlayer player = (GlowPlayer) holder;
            player.stopMapShowing();
        }
    }

    public void onShow(GlowHumanEntity holder, ItemStack me, ItemStack before) {
        if (holder instanceof GlowPlayer) {
            GlowPlayer player = (GlowPlayer) holder;
            player.startMapShowing(me.getDurability());
        }
    }
}
