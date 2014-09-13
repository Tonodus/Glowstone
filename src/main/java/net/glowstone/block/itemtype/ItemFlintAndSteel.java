package net.glowstone.block.itemtype;

import net.glowstone.EventFactory;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.ItemTable;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemFlintAndSteel extends ItemType {
    public ItemFlintAndSteel() {
        setMaxStackSize(1);
    }

    @Override
    public void rightClickBlock(GlowPlayer player, GlowBlock target, BlockFace face, ItemStack holding, Vector clickedLoc) {
        //TODO check whether we can set this block on fire
        setBlockOnFire(player, target, face, holding, clickedLoc);
    }

    private void setBlockOnFire(GlowPlayer player, GlowBlock clicked, BlockFace face, ItemStack holding, Vector clickedLoc) {
        GlowBlock fireBlock = clicked.getRelative(face);
        if (fireBlock.getType() != Material.AIR) {
            return;
        }

        BlockIgniteEvent event = EventFactory.onBlockIgnite(fireBlock, BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, player, null);
        if (event.isCancelled()) {
            player.setItemInHand(holding);
            return;
        }

        player.getWorld().playSound(player.getLocation(), Sound.FIRE_IGNITE, 1f, 0.8f);

        ItemStack afterUse = holding.clone();
        if (player.getGameMode() != GameMode.CREATIVE) {
            afterUse.setDurability((short) (afterUse.getDurability() + 1));
            if (afterUse.getDurability() == 65) {
                EventFactory.onPlayerItemBreakEvent(player, afterUse);
                afterUse = null;
            }
        }

        ItemTable.instance().getBlock(Material.FIRE).rightClickBlock(player, fireBlock.getRelative(BlockFace.DOWN), BlockFace.UP, holding, clickedLoc);

        player.setItemInHand(afterUse);
    }
}
