package net.glowstone.block.blocktype;

import net.glowstone.block.GlowBlock;
import org.bukkit.block.BlockFace;

public class BlockPlant extends BlockNeedsAttached {
    @Override
    public boolean canPlaceAt(GlowBlock block, BlockFace against) {
        switch (block.getRelative(BlockFace.DOWN).getType()) {
            case GRASS:
            case DIRT:
            case SOIL:
                return true;
            default:
                return false;
        }
    }
}
