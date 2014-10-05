package net.glowstone.block.itemtype;

import net.glowstone.block.GlowBlock;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public interface ItemTypeFeature {
    /*
    internal use only
     */
    void init(ItemType on);

    /*
    public use
     */
    void rightClickAir(GlowPlayer player, ItemStack holding);

    void rightClickBlock(GlowPlayer player, GlowBlock target, BlockFace face, ItemStack holding, Vector clickedLoc);
}
