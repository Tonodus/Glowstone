package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.block.GlowBlock;
import net.glowstone.util.BooleanOptional;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class BlockTallGrass extends BlockFeatureDrop {
    private final Random random = new Random();

    @Override
    public Collection<ItemStack> getBlockDrops(GlowBlock block) {
        if (random.nextFloat() < .125) {
            return Collections.unmodifiableList(Arrays.asList(new ItemStack(Material.SEEDS, 1)));
        }
        return Collections.unmodifiableList(Arrays.asList(new ItemStack[0]));
    }

    @Override
    public Optional<Boolean> canAbsorb(GlowBlock block, BlockFace face, ItemStack holding) {
        return BooleanOptional.TRUE;
    }

    @Override
    public Optional<Boolean> canOverride(GlowBlock block, BlockFace face, ItemStack holding) {
        return BooleanOptional.TRUE;
    }
}
