package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.util.BooleanOptional;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Ladder;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public class BlockLadder extends BlockClimbable {

    @Override
    public Optional<Boolean> canPlaceAt(GlowBlock block, BlockFace against) {
        Optional<Boolean> superResult = super.canPlaceAt(block, against);
        if (superResult.isPresent() && superResult.get() == true)
            return BooleanOptional.TRUE;

        return Optional.of(isTargetOccluding(block, BlockFace.SOUTH) ||
                isTargetOccluding(block, BlockFace.WEST) ||
                isTargetOccluding(block, BlockFace.NORTH) ||
                isTargetOccluding(block, BlockFace.EAST));
    }

    @Override
    public void placeBlock(GlowPlayer player, GlowBlockState state, BlockFace face, ItemStack holding, Vector clickedLoc) {
        super.placeBlock(player, state, face, holding, clickedLoc);

        MaterialData data = state.getData();
        if (data instanceof Ladder) {
            if (face != BlockFace.DOWN && face != BlockFace.UP && isTargetOccluding(state, face.getOppositeFace())) {
                ((Ladder) data).setFacingDirection(face.getOppositeFace());
            } else {
                if (isTargetOccluding(state, BlockFace.SOUTH)) {
                    ((Ladder) data).setFacingDirection(BlockFace.SOUTH);
                } else if (isTargetOccluding(state, BlockFace.WEST)) {
                    ((Ladder) data).setFacingDirection(BlockFace.WEST);
                } else if (isTargetOccluding(state, BlockFace.NORTH)) {
                    ((Ladder) data).setFacingDirection(BlockFace.NORTH);
                } else if (isTargetOccluding(state, BlockFace.EAST)) {
                    ((Ladder) data).setFacingDirection(BlockFace.EAST);
                } else {
                    return;
                }
            }

            state.setData(data);
        } else {
            warnMaterialData(Ladder.class, data);
        }
    }
}
