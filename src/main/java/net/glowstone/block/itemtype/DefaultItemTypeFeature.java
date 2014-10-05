package net.glowstone.block.itemtype;


import net.glowstone.block.GlowBlock;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DefaultItemTypeFeature implements ItemTypeFeature {
    protected ItemType relatedType = null;

    @Override
    public void init(ItemType on) {
        relatedType = on;
    }

    @Override
    public void rightClickAir(GlowPlayer player, ItemStack holding) {

    }

    @Override
    public void rightClickBlock(GlowPlayer player, GlowBlock target, BlockFace face, ItemStack holding, Vector clickedLoc) {

    }
}
