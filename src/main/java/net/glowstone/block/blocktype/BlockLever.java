package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.util.BooleanOptional;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Lever;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public class BlockLever extends BlockType {

    public BlockLever() {
        super(new BlockDirectDrops(Material.LEVER), new LeverFeature());
    }

    private static class LeverFeature extends DefaultBlockTypeFeature {
        @Override
        public Optional<Boolean> blockInteract(GlowPlayer player, GlowBlock block, BlockFace face, Vector clickedLoc) {
            final GlowBlockState state = block.getState();
            final MaterialData data = state.getData();

            if (!(data instanceof Lever)) {
                warnMaterialData(Lever.class, data);
                return BooleanOptional.FALSE;
            }

            final Lever lever = (Lever) data;
            lever.setPowered(!lever.isPowered());
            state.update();
            return BooleanOptional.TRUE;
        }

        @Override
        public void placeBlock(GlowPlayer player, GlowBlockState state, BlockFace face, ItemStack holding, Vector clickedLoc) {
            super.placeBlock(player, state, face, holding, clickedLoc);

            final MaterialData data = state.getData();

            if (!(data instanceof Lever)) {
                warnMaterialData(Lever.class, data);
                return;
            }

            final Lever lever = (Lever) data;
            BlockAttachable.setAttachedFace(state, face.getOppositeFace());
            lever.setFacingDirection(face == BlockFace.UP || face == BlockFace.DOWN ? player.getDirection() : face);

        }
    }
}
