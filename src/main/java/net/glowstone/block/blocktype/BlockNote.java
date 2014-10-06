package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.GlowChunk;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.entity.TENote;
import net.glowstone.block.entity.TileEntity;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.block.NoteBlock;
import org.bukkit.util.Vector;

public class BlockNote extends DefaultBlockTypeFeature {
    @Override
    public Optional<? extends TileEntity> createTileEntity(GlowChunk chunk, int cx, int cy, int cz) {
        return Optional.of(new TENote(chunk.getBlock(cx, cy, cz)));
    }

    @Override
    public Optional<Boolean> blockInteract(GlowPlayer player, GlowBlock block, BlockFace face, Vector clickedLoc) {
        return Optional.of(((NoteBlock) block.getState()).play());
    }
}
