package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.block.GlowBlock;
import net.glowstone.util.BooleanOptional;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

public class BlockFire extends BlockType {

    public BlockFire() {
        super(new BlockDropless(), new FireFeature());
    }

    private static class FireFeature extends DefaultBlockTypeFeature {
        @Override
        public Optional<Boolean> canOverride(GlowBlock block, BlockFace face, ItemStack holding) {
            return BooleanOptional.TRUE;
        }
    }
}
