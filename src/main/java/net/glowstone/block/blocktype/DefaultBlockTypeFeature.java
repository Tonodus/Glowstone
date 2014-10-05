package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.GlowChunk;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.block.entity.TileEntity;
import net.glowstone.block.itemtype.DefaultItemTypeFeature;
import net.glowstone.block.itemtype.ItemType;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.Collection;

public class DefaultBlockTypeFeature extends DefaultItemTypeFeature implements BlockTypeFeature {
    protected BlockFace getOppositeBlockFace(Location location, boolean invert) {
        return BlockType.getOppositeBlockFace(location, invert);
    }

    protected void warnMaterialData(Class<? extends MaterialData> clazz, MaterialData data) {
        ((BlockType) relatedType).warnMaterialData(clazz, data);
    }

    @Override
    public void init(ItemType on) {
        if (!(on instanceof BlockType))
            throw new IllegalArgumentException("DefaultBlockTypeFeature can only be initialised with BlockType, got " + on);

        super.init(on);
    }

    @Override
    public Optional<? extends Collection<ItemStack>> getDrops(GlowBlock block) {
        return Optional.absent();
    }

    @Override
    public Optional<? extends TileEntity> createTileEntity(GlowChunk chunk, int cx, int cy, int cz) {
        return Optional.absent();
    }

    @Override
    public Optional<Boolean> canPlaceAt(GlowBlock block, BlockFace against) {
        return Optional.absent();
    }

    @Override
    public void placeBlock(GlowPlayer player, GlowBlockState state, BlockFace face, ItemStack holding, Vector clickedLoc) {

    }

    @Override
    public void afterPlace(GlowPlayer player, GlowBlock block, ItemStack holding) {

    }

    @Override
    public Optional<Boolean> blockInteract(GlowPlayer player, GlowBlock block, BlockFace face, Vector clickedLoc) {
        return Optional.absent();
    }

    @Override
    public Optional<Boolean> canAbsorb(GlowBlock block, BlockFace face, ItemStack holding) {
        return Optional.absent();
    }

    @Override
    public Optional<Boolean> canOverride(GlowBlock block, BlockFace face, ItemStack holding) {
        return Optional.absent();
    }
}
