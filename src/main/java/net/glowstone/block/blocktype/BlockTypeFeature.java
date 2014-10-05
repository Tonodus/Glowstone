package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.GlowChunk;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.block.entity.TileEntity;
import net.glowstone.block.itemtype.ItemTypeFeature;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collection;

public interface BlockTypeFeature extends ItemTypeFeature {
    public Optional<? extends Collection<ItemStack>> getDrops(GlowBlock block);

    public Optional<? extends TileEntity> createTileEntity(GlowChunk chunk, int cx, int cy, int cz);

    public Optional<Boolean> canPlaceAt(GlowBlock block, BlockFace against);

    public void placeBlock(GlowPlayer player, GlowBlockState state, BlockFace face, ItemStack holding, Vector clickedLoc);

    public void afterPlace(GlowPlayer player, GlowBlock block, ItemStack holding);

    public Optional<Boolean> blockInteract(GlowPlayer player, GlowBlock block, BlockFace face, Vector clickedLoc);

    public Optional<Boolean> canAbsorb(GlowBlock block, BlockFace face, ItemStack holding);

    public Optional<Boolean> canOverride(GlowBlock block, BlockFace face, ItemStack holding);

}
