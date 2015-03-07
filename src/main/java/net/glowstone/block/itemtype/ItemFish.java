package net.glowstone.block.itemtype;

import net.glowstone.entity.GlowPlayer;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemFish extends ItemType {
    private static final PotionEffect PE_CONFUSION = new PotionEffect(PotionEffectType.CONFUSION, 300, 1),
            PE_POISON = new PotionEffect(PotionEffectType.POISON, 1200, 3),
            PE_HUNGER = new PotionEffect(PotionEffectType.HUNGER, 300, 2);

    @Override
    public void rightClickAir(GlowPlayer consumer, ItemStack holding) {
        if (consumer.getGameMode() != GameMode.CREATIVE && consumer.getGameMode() != GameMode.SPECTATOR && consumer.getFoodLevel() < 20) {
            consumer.tryConsuming(holding, 32);
        }
    }

    @Override
    public void consumed(GlowPlayer consumer, ItemStack item) {
        int addFoodLevel;
        float addSaturation;

        switch (item.getType()) {
            case RAW_FISH:
                if (item.getDurability() == 0 || item.getDurability() == 1) {
                    addFoodLevel = 2;
                    addSaturation = 0.4f;
                } else if (item.getDurability() == 2) {
                    addFoodLevel = 1;
                    addSaturation = 0.2f;
                } else {
                    addFoodLevel = 1;
                    addSaturation = 0.2f;
                    consumer.addPotionEffect(PE_CONFUSION);
                    consumer.addPotionEffect(PE_HUNGER);
                    consumer.addPotionEffect(PE_POISON);
                }
                break;
            case COOKED_FISH:
                if (item.getDurability() == 0) {
                    addFoodLevel = 5;
                    addSaturation = 6;
                } else {
                    addFoodLevel = 6;
                    addSaturation = 9.6f;
                }
                break;
            default:
                throw new IllegalArgumentException("ItemFish cannot handle consuming " + item);
        }

        consumer.saturateNormally(addFoodLevel, addSaturation);
    }

}
