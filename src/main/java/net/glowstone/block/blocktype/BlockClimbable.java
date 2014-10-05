package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import org.bukkit.block.BlockFace;

public abstract class BlockClimbable extends DefaultBlockTypeFeature {

    @Override
    public Optional<Boolean> canPlaceAt(GlowBlock block, BlockFace against) {
        return Optional.of(against != BlockFace.DOWN && against != BlockFace.UP && isTargetOccluding(block, against.getOppositeFace()));
    }

    protected boolean isTargetOccluding(GlowBlockState state, BlockFace face) {
        return isTargetOccluding(state.getBlock(), face);
    }

    protected boolean isTargetOccluding(GlowBlock block, BlockFace face) {
        return block.getRelative(face).getType().isOccluding();
    }
}
