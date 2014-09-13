package net.glowstone.block.blocktype;

import net.glowstone.GlowWorld;
import net.glowstone.block.GlowBlock;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class BlockFire extends BlockDropless {
    private static final Random random = new Random();

    @Override
    public boolean canOverride(GlowBlock block, BlockFace face, ItemStack holding) {
        return true;
    }

    @Override
    public void afterPlace(GlowPlayer player, GlowBlock block, ItemStack holding) {

    }

    public void onTick(GlowBlock blockFire) {
        if (!fireAllowedByGameRule(blockFire.getWorld()))
            return;

        boolean endlessFire = isEndlessFire(blockFire);
        if (!endlessFire && isRainyAround(blockFire)) {
            extinguish();
            return;
        }

        byte data = blockFire.getData();
        if (data <= 15)
            data += random.nextInt(3) / 2;
        blockFire.setData(data);

        boolean isWet = isBlockWet(blockFire);

        int wet = isWet ? 250 : 300;

        fireAround(blockFire, wet, data);

        //for each block expect me
        while (false) {
            byte dx = 0;
            if (dx < 0)
                continue;
            int difficulty = dx;
            if (isWet)
                difficulty /= 2;
        }
    }

    private boolean isBlockWet(GlowBlock blockFire) {
        return false; //TODO
    }

    private boolean fireAllowedByGameRule(GlowWorld world) {
        return true; //TODO
    }

    private boolean isRainyAround(GlowBlock blockFire) {
        World world = blockFire.getWorld();
        world.isR
    }

    private boolean isEndlessFire(GlowBlock blockFire) {
        return false; //TODO
    }

    private void extinguish() {

    }
}
