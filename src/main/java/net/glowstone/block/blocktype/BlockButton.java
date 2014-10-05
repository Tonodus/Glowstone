package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.util.BooleanOptional;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Button;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BlockButton extends BlockType {

    public BlockButton(Material material) {
        super(new BlockDirectDrops(material), new ButtonFeature());
    }

    private static class ButtonFeature extends DefaultBlockTypeFeature {
        @Override
        public Optional<Boolean> blockInteract(GlowPlayer player, GlowBlock block, BlockFace face, Vector clickedLoc) {
            final GlowBlockState state = block.getState();
            final MaterialData data = state.getData();

            if (!(data instanceof Button)) {
                warnMaterialData(Button.class, data);
                return BooleanOptional.FALSE;
            }

            final Button button = (Button) data;

            if (button.isPowered()) {
                return BooleanOptional.TRUE;
            }

            button.setPowered(true);
            state.update();

            // todo: switch to block scheduling system when one is available
            (new BukkitRunnable() {
                @Override
                public void run() {
                    button.setPowered(false);
                    state.update();
                }
            }).runTaskLater(null, 20);

            return BooleanOptional.TRUE;
        }

        @Override
        public void placeBlock(GlowPlayer player, GlowBlockState state, BlockFace face, ItemStack holding, Vector clickedLoc) {
            super.placeBlock(player, state, face, holding, clickedLoc);

            MaterialData data = state.getData();

            if (!(data instanceof Button)) {
                warnMaterialData(Button.class, data);
                return;
            }

            BlockAttachable.setAttachedFace(state, face.getOppositeFace());
        }
    }
}
