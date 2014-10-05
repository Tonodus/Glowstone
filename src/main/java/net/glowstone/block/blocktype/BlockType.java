package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.EventFactory;
import net.glowstone.GlowChunk;
import net.glowstone.GlowServer;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.block.ItemTable;
import net.glowstone.block.entity.TileEntity;
import net.glowstone.block.itemtype.ItemType;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Base class for specific types of blocks.
 */
public class BlockType extends ItemType {
    private final Collection<BlockTypeFeature> features;

    public BlockType(BlockTypeFeature... features) {
        super();
        this.features = new LinkedList<>();
        for (BlockTypeFeature feature : features) {
            this.features.add(feature);
            feature.init(this);
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    // Public accessors

    /**
     * Get the items that will be dropped by digging the block.
     *
     * @param block The block being dug.
     * @return The drops that should be returned.
     */
    public final Collection<ItemStack> getDrops(GlowBlock block) {
        Collection<ItemStack> drops = new LinkedList<>();
        boolean present = false;

        for (BlockTypeFeature feature : features) {
            Optional<? extends Collection<ItemStack>> result = feature.getDrops(block);
            if (result.isPresent()) {
                drops.addAll(result.get());
                present = true;
            }
        }

        if (!present) {
            // default calculation
            return Arrays.asList(new ItemStack(block.getType(), 1, block.getData()));
        } else {
            return drops;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Actions

    /**
     * Create a new tile entity at the given location.
     *
     * @param chunk The chunk to create the tile entity at.
     * @param cx The x coordinate in the chunk.
     * @param cy The y coordinate in the chunk.
     * @param cz The z coordinate in the chunk.
     * @return The new TileEntity, or null if no tile entity is used.
     */
    public final TileEntity createTileEntity(GlowChunk chunk, int cx, int cy, int cz) {
        for (BlockTypeFeature feature : features) {
            Optional<TileEntity> result = feature.createTileEntity(chunk, cx, cy, cz);
            if (result.isPresent())
                return result.get();
        }

        return null;
    }

    /**
     * Check whether the block can be placed at the given location.
     *
     * @param block The location the block is being placed at.
     * @param against The face the block is being placed against.
     * @return Whether the placement is valid.
     */
    public final boolean canPlaceAt(GlowBlock block, BlockFace against) {
        for (BlockTypeFeature feature : features) {
            Optional<Boolean> result = feature.canPlaceAt(block, against);
            if (result.isPresent())
                return result.get();
        }

        return true;
    }

    /**
     * Called when a block is placed to calculate what the block will become.
     *
     * @param player the player who placed the block
     * @param state the BlockState to edit
     * @param holding the ItemStack that was being held
     * @param face the face off which the block is being placed
     * @param clickedLoc where in the block the click occurred
     */
    public final void placeBlock(GlowPlayer player, GlowBlockState state, BlockFace face, ItemStack holding, Vector clickedLoc) {
        state.setType(getMaterial());
        state.setRawData((byte) holding.getDurability());

        for (BlockTypeFeature feature : features)
            feature.placeBlock(player, state, face, holding, clickedLoc);
    }

    /**
     * Called after a block has been placed by a player.
     *
     * @param player the player who placed the block
     * @param block the block that was placed
     * @param holding the the ItemStack that was being held
     */
    public final void afterPlace(GlowPlayer player, GlowBlock block, ItemStack holding) {
        for (BlockTypeFeature feature : features)
            feature.afterPlace(player, block, holding);
    }

    /**
     * Called when a player attempts to interact with (right-click) a block of
     * this type already in the world.
     *
     * @param player the player interacting
     * @param block the block interacted with
     * @param face the clicked face
     * @param clickedLoc where in the block the click occurred
     * @return Whether the interaction occurred.
     */
    public final boolean blockInteract(GlowPlayer player, GlowBlock block, BlockFace face, Vector clickedLoc) {
        for (BlockTypeFeature feature : features) {
            Optional<Boolean> result = feature.blockInteract(player, block, face, clickedLoc);
            if (result.isPresent())
                return result.get();
        }

        return false;
    }

    /**
     * Called when a player attempts to place a block on an existing block of
     * this type. Used to determine if the placement should occur into the air
     * adjacent to the block (normal behavior), or absorbed into the block
     * clicked on.
     *
     * @param block The block the player right-clicked
     * @param face The face on which the click occurred
     * @param holding The ItemStack the player was holding
     * @return Whether the place should occur into the block given.
     */
    public boolean canAbsorb(GlowBlock block, BlockFace face, ItemStack holding) {
        for (BlockTypeFeature feature : features) {
            Optional<Boolean> result = feature.canAbsorb(block, face, holding);
            if (result.isPresent())
                return result.get();
        }

        return false;
    }

    /**
     * Called to check if this block can be overridden by a block place
     * which would occur inside it.
     *
     * @param block The block being targeted by the placement
     * @param face The face on which the click occurred
     * @param holding The ItemStack the player was holding
     * @return Whether this block can be overridden.
     */
    public boolean canOverride(GlowBlock block, BlockFace face, ItemStack holding) {
        for (BlockTypeFeature feature : features) {
            Optional<Boolean> result = feature.canOverride(block, face, holding);
            if (result.isPresent())
                return result.get();
        }

        return block.isLiquid();
    }

    @Override
    public final void rightClickBlock(GlowPlayer player, GlowBlock against, BlockFace face, ItemStack holding, Vector clickedLoc) {
        GlowBlock target = against.getRelative(face);

        // check whether the block clicked against should absorb the placement
        BlockType againstType = ItemTable.instance().getBlock(against.getTypeId());
        if (againstType.canAbsorb(against, face, holding)) {
            target = against;
        } else if (!target.isEmpty()) {
            // air can always be overridden
            BlockType targetType = ItemTable.instance().getBlock(target.getTypeId());
            if (!targetType.canOverride(target, face, holding)) {
                return;
            }
        }

        // call canBuild event
        boolean canBuild = canPlaceAt(target, face);
        BlockCanBuildEvent canBuildEvent = new BlockCanBuildEvent(target, getId(), canBuild);
        if (!EventFactory.callEvent(canBuildEvent).isBuildable()) {
            //revert(player, target);
            return;
        }

        // grab states and update block
        GlowBlockState oldState = target.getState(), newState = target.getState();
        placeBlock(player, newState, face, holding, clickedLoc);
        newState.update(true);

        // call blockPlace event
        BlockPlaceEvent event = new BlockPlaceEvent(target, oldState, against, holding, player, canBuild);
        EventFactory.callEvent(event);
        if (event.isCancelled() || !event.canBuild()) {
            oldState.update(true);
            return;
        }

        // play a sound effect
        // todo: vary sound effect based on block type
        target.getWorld().playSound(target.getLocation(), Sound.DIG_WOOD, 1, 1);

        // do any after-place actions
        afterPlace(player, target, holding);

        // deduct from stack if not in creative mode
        if (player.getGameMode() != GameMode.CREATIVE) {
            holding.setAmount(holding.getAmount() - 1);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Helper methods

    /**
     * Display the warning for finding the wrong MaterialData subclass.
     *
     * @param clazz The expected subclass of MaterialData.
     * @param data The actual MaterialData found.
     */
    void warnMaterialData(Class<? extends MaterialData> clazz, MaterialData data) {
        GlowServer.logger.warning("Wrong MaterialData for " + getMaterial() + " (" + getClass().getSimpleName() + "): expected " + clazz.getSimpleName() + ", got " + data);
    }

    /**
     * Gets the BlockFace opposite of the direction the location is facing.
     * Usually used to set the way container blocks face when being placed.
     *
     * @param location Location to get opposite of
     * @param inverted If up/down should be used
     * @return Opposite BlockFace or EAST if pitch is invalid
     */
    static BlockFace getOppositeBlockFace(Location location, boolean inverted) {
        double rot = location.getYaw() % 360;
        if (inverted) {
            // todo: Check the 67.5 pitch in source. This is based off of WorldEdit's number for this.
            double pitch = location.getPitch();
            if (pitch < -67.5D) {
                return BlockFace.DOWN;
            } else if (pitch > 67.5D) {
                return BlockFace.UP;
            }
        }
        if (rot < 0) {
            rot += 360.0;
        }
        if (0 <= rot && rot < 45) {
            return BlockFace.NORTH;
        } else if (45 <= rot && rot < 135) {
            return BlockFace.EAST;
        } else if (135 <= rot && rot < 225) {
            return BlockFace.SOUTH;
        } else if (225 <= rot && rot < 315) {
            return BlockFace.WEST;
        } else if (315 <= rot && rot < 360.0) {
            return BlockFace.NORTH;
        } else {
            return BlockFace.EAST;
        }
    }
}
