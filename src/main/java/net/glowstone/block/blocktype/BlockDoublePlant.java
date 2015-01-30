package net.glowstone.block.blocktype;

import net.glowstone.block.GlowBlock;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.DoublePlantSpecies;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.DoublePlant;

import java.util.Arrays;
import java.util.Collection;

public class BlockDoublePlant extends BlockPlant {

    @Override
    public boolean canPlaceAt(GlowBlock block, BlockFace against) {
        return super.canPlaceAt(block, against) && block.getRelative(BlockFace.UP).getType() == Material.AIR;
    }

    @Override
    public void afterPlace(GlowPlayer player, GlowBlock block, ItemStack holding) {
        GlowBlock otherPart = block.getRelative(BlockFace.UP);
        otherPart.setType(Material.DOUBLE_PLANT);
        BlockState state = otherPart.getState();
        ((DoublePlant) state.getData()).setSpecies(DoublePlantSpecies.PLANT_APEX);
        state.update();
    }

    @Override
    public void updatePhysics(GlowBlock me) {
        if (((DoublePlant) me.getState().getData()).getSpecies() == DoublePlantSpecies.PLANT_APEX)
            return;

        super.updatePhysics(me);
    }

    @Override
    protected void dropMe(GlowBlock me) {
        me.getWorld().dropItemNaturally(me.getLocation(), new ItemStack(Material.DOUBLE_PLANT, 2, me.getData()));

        me.setType(Material.AIR, false);
        me.getRelative(BlockFace.UP).setType(Material.AIR, false);
    }

    @Override
    public Collection<ItemStack> getDrops(GlowBlock me, ItemStack tool) {
        ItemStack dropping;
        if (((DoublePlant) me.getState().getData()).getSpecies() == DoublePlantSpecies.PLANT_APEX) {
            dropping = new ItemStack(Material.DOUBLE_PLANT, 1, me.getRelative(BlockFace.DOWN).getData());
        } else {
            dropping = new ItemStack(Material.DOUBLE_PLANT, 1, me.getData());
        }

        return Arrays.asList(dropping);
    }

    @Override
    public void onBlockChanged(GlowBlock block, Material oldType, byte oldData, Material newType, byte data) {
        if (newType == Material.DOUBLE_PLANT) return;

        if (oldData >= 8 && oldData <= 15) {
            block.getRelative(BlockFace.DOWN).setType(Material.AIR, false);
        } else {
            block.getRelative(BlockFace.UP).setType(Material.AIR, false);
        }
    }
}
