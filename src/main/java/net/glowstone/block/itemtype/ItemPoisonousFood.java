package net.glowstone.block.itemtype;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemPoisonousFood extends ItemPotionEffectFood {
    public ItemPoisonousFood(float chance, int addFoodLevel, float addSaturation) {
        super(chance, addFoodLevel, addSaturation);
    }

    @Override
    protected PotionEffect getPotionEffect() {
        return new PotionEffect(PotionEffectType.POISON, 80, 0);
    }
}
