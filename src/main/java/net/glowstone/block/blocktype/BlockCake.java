package net.glowstone.block.blocktype;

import net.glowstone.block.GlowBlock;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class BlockCake extends BlockType {
    @Override
    public boolean blockInteract(GlowPlayer player, GlowBlock block, BlockFace face, Vector clickedLoc) {
        if (player.getFoodLevel() < 20 && player.getGameMode() != GameMode.CREATIVE) {
            if (player.saturateNormally(2, 0.4f)) {
                if (block.getData() < 6) {
                    block.setData((byte) (block.getData() + 1));
                } else {
                    block.setType(Material.AIR);
                }
            }
        }

        return true;
    }
}
