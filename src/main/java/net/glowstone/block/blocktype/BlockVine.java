package net.glowstone.block.blocktype;

import com.google.common.base.Optional;
import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.util.BooleanOptional;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Vine;
import org.bukkit.util.Vector;

public class BlockVine extends BlockClimbable {

    @Override
    public Optional<Boolean> canPlaceAt(GlowBlock block, BlockFace against) {
        Optional<Boolean> result = super.canPlaceAt(block, against);
        if (result.isPresent() && result.get())
            return BooleanOptional.TRUE;

        return Optional.of(against == BlockFace.UP && isTargetOccluding(block, BlockFace.UP));
    }

    @Override
    public void placeBlock(GlowPlayer player, GlowBlockState state, BlockFace face, ItemStack holding, Vector clickedLoc) {
        super.placeBlock(player, state, face, holding, clickedLoc);

        MaterialData data = state.getData();
        if (data instanceof Vine) {
            if (face == BlockFace.DOWN || face == BlockFace.UP) {
                return;
            } else {
                ((Vine) data).putOnFace(face.getOppositeFace());
            }
            state.setData(data);
        } else {
            warnMaterialData(Vine.class, data);
        }
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
