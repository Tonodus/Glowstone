package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.GlowChunk;
import net.glowstone.block.entity.TEDropper;
import net.glowstone.block.entity.TileEntity;
import org.bukkit.Material;

public class BlockDropper extends BlockType {

    public BlockDropper() {
        super(new BlockDirectDrops(Material.DROPPER), new DropperFeature());
    }

    private static class DropperFeature extends BlockDispenser.DispenserFeature {
        @Override
        public Optional<? extends TileEntity> createTileEntity(GlowChunk chunk, int cx, int cy, int cz) {
            return Optional.of(new TEDropper(chunk.getBlock(cx, cy, cz)));
        }
    }

}
