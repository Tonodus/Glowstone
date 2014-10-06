package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.GlowChunk;
import net.glowstone.block.entity.TEMobSpawner;
import net.glowstone.block.entity.TileEntity;

public class BlockMobSpawner extends BlockDropless {
    @Override
    public Optional<? extends TileEntity> createTileEntity(GlowChunk chunk, int cx, int cy, int cz) {
        return Optional.of(new TEMobSpawner(chunk.getBlock(cx, cy, cz)));
    }
}
