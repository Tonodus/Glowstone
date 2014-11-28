package net.glowstone.net.handler.play.inv;

import com.flowpowered.networking.MessageHandler;
import net.glowstone.inventory.EnchantmentManager;
import net.glowstone.net.GlowSession;
import net.glowstone.net.message.play.inv.EnchantItemMessage;

public final class EnchantItemHandler implements MessageHandler<GlowSession, EnchantItemMessage> {
    @Override
    public void handle(GlowSession session, EnchantItemMessage message) {
        EnchantmentManager manager = session.getPlayer().getEnchantmentManager();
        if (manager.isPlayerEnchanting()) {
            manager.onPlayerEnchant(message.getEnchantment());
        } else {
            //TODO: player sent wrong packet... ignore?
        }
    }
}
