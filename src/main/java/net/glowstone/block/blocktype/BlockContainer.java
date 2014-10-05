package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.entity.TEContainer;
import net.glowstone.block.entity.TileEntity;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.util.BooleanOptional;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Base BlockType for containers.
 */
public class BlockContainer extends BlockFeatureDrop {
    @Override
    public Optional<Boolean> blockInteract(GlowPlayer player, GlowBlock block, BlockFace face, Vector clickedLoc) {
        TileEntity te = block.getTileEntity();
        if (te instanceof TEContainer) {
            // todo: animation?
            player.openInventory(((TEContainer) te).getInventory());
            return BooleanOptional.TRUE;
        }
        return BooleanOptional.FALSE;
    }

    @Override
    public Collection<ItemStack> getBlockDrops(GlowBlock block) {
        LinkedList<ItemStack> list = new LinkedList<>();

        list.add(new ItemStack(block.getType(), 1));

        for (ItemStack i : ((TEContainer) block.getTileEntity()).getInventory().getContents()) {
            if (i != null) {
                list.add(i);
            }
        }
        return list;
    }

}
