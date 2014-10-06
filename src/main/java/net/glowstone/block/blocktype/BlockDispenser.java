package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.GlowChunk;
import net.glowstone.block.GlowBlockState;
import net.glowstone.block.entity.TEDispenser;
import net.glowstone.block.entity.TileEntity;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dispenser;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public class BlockDispenser extends BlockType {

    public BlockDispenser() {
        super(new BlockDirectDrops(Material.DISPENSER), new DispenserFeature());
    }

    protected static class DispenserFeature extends BlockContainer {
        @Override
        public Optional<? extends TileEntity> createTileEntity(GlowChunk chunk, int cx, int cy, int cz) {
            return Optional.of(new TEDispenser(chunk.getBlock(cx, cy, cz)));
        }

        @Override
        public void placeBlock(GlowPlayer player, GlowBlockState state, BlockFace face, ItemStack holding, Vector clickedLoc) {
            super.placeBlock(player, state, face, holding, clickedLoc);
            final MaterialData data = state.getData();
            if (data instanceof Dispenser) {
                ((Dispenser) data).setFacingDirection(getOppositeBlockFace(player.getLocation(), true));
                state.setData(data);
            } else {
                warnMaterialData(Dispenser.class, data);
            }
        }

    }
}
