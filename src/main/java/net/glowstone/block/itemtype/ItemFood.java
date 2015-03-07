package net.glowstone.block.itemtype;

import net.glowstone.entity.GlowPlayer;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

public class ItemFood extends ItemType {
    private final int addFoodLevel;
    private final float addSaturation;

    public ItemFood(int addFoodLevel, float addSaturation) {
        this.addFoodLevel = addFoodLevel;
        this.addSaturation = addSaturation;
    }

    @Override
    public void rightClickAir(GlowPlayer consumer, ItemStack holding) {
        if (consumer.getGameMode() != GameMode.CREATIVE && consumer.getGameMode() != GameMode.SPECTATOR && consumer.getFoodLevel() < 20) {
            consumer.tryConsuming(holding, 32);
        }
    }

    @Override
    public boolean consumed(GlowPlayer consumer, ItemStack item) {
        consumer.saturateNormally(addFoodLevel, addSaturation);
        return true;
    }
}
