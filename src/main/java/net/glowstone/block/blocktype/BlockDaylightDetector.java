package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.block.GlowBlock;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.util.BooleanOptional;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class BlockDaylightDetector extends DefaultBlockTypeFeature {

    @Override
    public Optional<Boolean> blockInteract(GlowPlayer player, GlowBlock block, BlockFace face, Vector clickedLoc) {
        if (block.getType() == Material.DAYLIGHT_DETECTOR) {
            block.setType(Material.DAYLIGHT_DETECTOR_INVERTED);
        } else {
            block.setType(Material.DAYLIGHT_DETECTOR);
        }
        return BooleanOptional.TRUE;
    }
}
