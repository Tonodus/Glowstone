package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.GlowChunk;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.block.entity.TESign;
import net.glowstone.block.entity.TileEntity;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BlockSign extends BlockType {

    public BlockSign() {
        super(new BlockDirectDrops(Material.SIGN), new SignFeature());
    }

    private static class SignFeature extends DefaultBlockTypeFeature {
        @Override
        public Optional<? extends TileEntity> createTileEntity(GlowChunk chunk, int cx, int cy, int cz) {
            return Optional.of(new TESign(chunk.getBlock(cx, cy, cz)));
        }

        @Override
        public void placeBlock(GlowPlayer player, GlowBlockState state, BlockFace face, ItemStack holding, Vector clickedLoc) {
            state.setType(relatedType.getMaterial());
            byte data;
            if (relatedType.getMaterial() == Material.WALL_SIGN) {
                // attach to appropriate side of wall
                data = getFacing(face);
            } else {
                // calculate the facing of the sign based on the angle between the player and the post
                Location loc = player.getLocation();
                // 22.5 = 360 / 16
                long facing = Math.round(loc.getYaw() / 22.5) + 8;
                data = (byte) (((facing % 16) + 16) % 16);
            }
            state.setRawData(data);
        }

        @Override
        public void afterPlace(GlowPlayer player, GlowBlock block, ItemStack holding) {
            player.openSignEditor(block.getLocation());
        }

        private byte getFacing(BlockFace face) {
            switch (face) {
                case NORTH:
                    return 2;
                case SOUTH:
                    return 3;
                case WEST:
                    return 4;
                case EAST:
                    return 5;
            }
            return 0;
        }
    }
}
