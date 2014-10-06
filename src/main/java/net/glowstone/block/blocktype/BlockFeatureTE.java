package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.GlowChunk;
import net.glowstone.block.entity.TileEntity;

public abstract class BlockFeatureTE extends DefaultBlockTypeFeature {
    @Override
    public final Optional<? extends TileEntity> createTileEntity(GlowChunk chunk, int cx, int cy, int cz) {
        return Optional.of(createTE(chunk, cx, cy, cz));
    }

    protected abstract TileEntity createTE(GlowChunk chunk, int cx, int cy, int cz);
}
