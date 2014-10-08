package net.glowstone.map;

import org.bukkit.entity.Player;
import org.bukkit.map.*;

/**
 * Glowstone's built-in map renderer.
 */
public final class GlowMapRenderer extends MapRenderer {

    private final GlowMapView map;

    public GlowMapRenderer(GlowMapView map) {
        super(false);
        this.map = map;
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        // todo
        ((GlowMapCanvas) canvas).clear();
        int offX = player.getLocation().getBlockX() + (GlowMapCanvas.MAP_SIZE / 2) - map.getCenterX();
        int offZ = player.getLocation().getBlockZ() + (GlowMapCanvas.MAP_SIZE / 2) - map.getCenterZ();
        canvas.drawText(offX, offZ, MinecraftFont.Font, player.getDisplayName());
        canvas.setPixel(offX, offZ, MapPalette.BLUE);

        /*
        |-------|
        |       |
        |  ==   |
        |       |
        |-------|
         */
        for (int i = 0; i < 20; i++)
            canvas.setPixel(10 + i, 10, MapPalette.BLUE);
    }

}
