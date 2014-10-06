package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.block.GlowBlock;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public abstract class BlockFeatureDrop extends DefaultBlockTypeFeature {
    @Override
    public final Optional<? extends Collection<ItemStack>> getDrops(GlowBlock block) {
        return Optional.of(getBlockDrops(block));
    }

    protected abstract Collection<ItemStack> getBlockDrops(GlowBlock block);
}
