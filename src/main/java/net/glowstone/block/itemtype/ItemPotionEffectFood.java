package net.glowstone.block.itemtype;

import net.glowstone.entity.GlowPlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class ItemPotionEffectFood extends ItemFood {
    private final Random random = new Random(); //TODO use world's random instance
    private final float chance;
    private final PotionEffect effect;


    public ItemPotionEffectFood(float chance, int addFoodLevel, float addSaturation) {
        super(addFoodLevel, addSaturation);
        this.chance = chance;
        this.effect = getPotionEffect();
    }

    @Override
    public void consumed(GlowPlayer consumer, ItemStack item) {
        super.consumed(consumer, item);

        if (random.nextFloat() <= chance) {
            consumer.addPotionEffect(effect);
        }
    }

    protected PotionEffect getPotionEffect() {
        return new PotionEffect(PotionEffectType.HUNGER, 600, 0);
    }
}
