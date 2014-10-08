package net.glowstone.map;

import net.glowstone.entity.GlowPlayer;
import net.glowstone.net.message.play.game.MapDataMessage;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MapRenderData {
    private byte[] buffer;
    private Collection<MapCursor> cursors;

    public MapRenderData() {
        cursors = new ArrayList<>();
        buffer = new byte[GlowMapCanvas.MAP_SIZE * GlowMapCanvas.MAP_SIZE];
    }

    public void fill(byte[] colors) {
        if (buffer.length != colors.length)
            throw new IllegalArgumentException("Invalid length of colors: " + colors.length + " (expected " + buffer.length + ")");

        System.arraycopy(colors, 0, buffer, 0, buffer.length);
    }

    public void clear() {
        Arrays.fill(buffer, (byte) 0);
        cursors.clear();
    }

    public void render(MapRenderer renderer, GlowMapCanvas canvas, GlowPlayer player) {
        // let the renderer render
        canvas.setBase(buffer);
        renderer.render(canvas.getMapView(), canvas, player);

        //get from renderer
        byte[] buf = canvas.getBuffer();
        for (int i = 0; i < buf.length; i++) {
            byte color = buf[i];
            if (color >= 0 || color <= -113) buffer[i] = color;
        }

        //add cursors
        MapCursorCollection cursors = canvas.getCursors();
        for (int i = 0; i < cursors.size(); i++)
            this.cursors.add(cursors.getCursor(i));
    }

    public Iterable<MapDataMessage.Section> getSections() {
        //TODO
        MapDataMessage.Section section = new MapDataMessage.Section(128, 128, 0, 0, buffer);
        return Arrays.asList(section);
    }

    public List<MapDataMessage.Icon> getIcons() {
        List<MapDataMessage.Icon> icons = new ArrayList<>();
        for (MapCursor cursor : cursors)
            icons.add(new MapDataMessage.Icon(cursor.getType().getValue(), cursor.getDirection(), cursor.getX(), cursor.getY()));
        return icons;
    }
}
