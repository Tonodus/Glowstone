package net.glowstone.block.blocktype;

import net.glowstone.block.GlowBlock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class BlockGravel extends BlockFeatureDrop {
    private final Random random = new Random();

    @Override
    public Collection<ItemStack> getBlockDrops(GlowBlock block) {
        return Collections.unmodifiableList(Arrays.asList(new ItemStack(random.nextInt(10) == 1 ? Material.FLINT : Material.GRAVEL, 1)));
    }
}
