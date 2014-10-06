package net.glowstone.block.blocktype;

import net.glowstone.block.GlowBlock;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class BlockDropless extends BlockFeatureDrop {
    private final Collection<ItemStack> emptyStack = Collections.unmodifiableList(Arrays.asList(new ItemStack[0]));

    @Override
    public final Collection<ItemStack> getBlockDrops(GlowBlock block) {
        return emptyStack;
    }
}
