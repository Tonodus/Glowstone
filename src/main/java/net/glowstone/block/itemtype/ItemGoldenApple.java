package net.glowstone.block.itemtype;

import net.glowstone.entity.GlowPlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemGoldenApple extends ItemFood {
    private static final PotionEffect PE_ABSORPTION = new PotionEffect(PotionEffectType.ABSORPTION, 2400, 0),
            PE_REGENERATION_1 = new PotionEffect(PotionEffectType.REGENERATION, 100, 1),
            PE_REGENERATION_2 = new PotionEffect(PotionEffectType.REGENERATION, 600, 4),
            PE_FIRE_RESISTANCE = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 6000, 0),
            PE_RESISTANCE = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000, 0);

    public ItemGoldenApple() {
        super(4, 9.6f);
    }

    @Override
    public boolean consumed(GlowPlayer consumer, ItemStack item) {
        super.consumed(consumer, item);

        consumer.addPotionEffect(PE_ABSORPTION);
        if (item.getDurability() == 0) {
            consumer.addPotionEffect(PE_REGENERATION_1);
        } else {
            consumer.addPotionEffect(PE_REGENERATION_2);
            consumer.addPotionEffect(PE_FIRE_RESISTANCE);
            consumer.addPotionEffect(PE_RESISTANCE);
        }

        return true;
    }
}
