package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.block.GlowBlock;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class BlockWorkbench extends DefaultBlockTypeFeature {
    @Override
    public Optional<Boolean> blockInteract(GlowPlayer player, GlowBlock block, BlockFace face, Vector clickedLoc) {
        return Optional.of(player.openWorkbench(block.getLocation(), false) != null);
    }
}
