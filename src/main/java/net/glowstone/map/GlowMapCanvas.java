package net.glowstone.map;

import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapPalette;

import java.awt.*;
import java.util.Arrays;

/**
 * Represents a canvas for drawing to a map. Each canvas is associated with a
 * specific {@link org.bukkit.map.MapRenderer} and represents that renderer's layer on the map.
 */
public final class GlowMapCanvas implements MapCanvas {

    public static final int MAP_SIZE = 128;

    private MapCursorCollection cursors = new MapCursorCollection();
    private final byte[] buffer = new byte[MAP_SIZE * MAP_SIZE];
    private final GlowMapView mapView;
    private byte[] base;

    protected GlowMapCanvas(GlowMapView mapView) {
        this.mapView = mapView;
    }

    @Override
    public GlowMapView getMapView() {
        return mapView;
    }

    @Override
    public MapCursorCollection getCursors() {
        return cursors;
    }

    @Override
    public void setCursors(MapCursorCollection cursors) {
        this.cursors = cursors;
    }

    @Override
    public void setPixel(int x, int y, byte color) {
        if (x < 0 || y < 0 || x >= MAP_SIZE || y >= MAP_SIZE) return;
        if (buffer[y * MAP_SIZE + x] != color) {
            buffer[y * MAP_SIZE + x] = color;
            // todo: mark dirty
        }
    }

    @Override
    public byte getPixel(int x, int y) {
        if (x < 0 || y < 0 || x >= MAP_SIZE || y >= MAP_SIZE) return 0;
        return buffer[y * MAP_SIZE + x];
    }

    @Override
    public byte getBasePixel(int x, int y) {
        if (x < 0 || y < 0 || x >= MAP_SIZE || y >= MAP_SIZE) return 0;
        return base[y * MAP_SIZE + x];
    }

    protected void setBase(byte[] base) {
        this.base = base;
    }

    protected byte[] getBuffer() {
        return buffer;
    }

    @Override
    public void drawImage(int x, int y, Image image) {
        byte[] pixels = MapPalette.imageToBytes(image);
        final int imageWidth = image.getWidth(null), imageHeight = image.getHeight(null);

        for (int ax = x; ax < imageWidth; ax++) {
            for (int ay = y; ay < imageHeight; ay++) {
                setPixel(ax, ay, pixels[(ay - y) * imageWidth + ax]);
            }
        }
    }

    @Override
    public void drawText(final int x, final int y, MapFont font, String text) {
        if (!font.isValid(text))
            throw new IllegalArgumentException("String '" + text + "' cannot be drawn to MapCanvas! (Contains invalid chars)");

        int ax = x, ay = y;
        byte color = MapPalette.DARK_GRAY;

        for (int i = 0; i < text.length(); i++) {
            char acChar = text.charAt(i);
            switch (acChar) {
                case '\n':
                    //start new line
                    ax = x;
                    ay += font.getHeight();
                    continue;
                case '§':
                    // change color
                    //TODO: Do we support "normal" MC-Color codes too? Like '§f' instead of '§32;' for white?

                    int ccLength = text.indexOf(';', i);
                    if (ccLength > -1) {
                        try {
                            color = Byte.parseByte(text.substring(i + 1, ccLength));
                            continue;
                        } catch (NumberFormatException e) {
                            //invalid color code
                        }
                    }
            }

            // just draw it
            ax += drawChar(ax, ay, font, acChar, color);
        }
    }

    /**
     * Draws a single char and returns the width of this char
     */
    private int drawChar(final int x, final int y, MapFont font, char acChar, byte color) {
        MapFont.CharacterSprite sprite = font.getChar(acChar);
        int maxW = Math.max(sprite.getWidth(), MAP_SIZE - x);
        int maxH = Math.min(sprite.getHeight(), MAP_SIZE - y);

        for (int ay = 0; ay < maxH; ay++) {
            for (int ax = 0; ax < maxW; ax++) {
                if (sprite.get(ax, ay)) {
                    setPixel(ax + x, ay + y, color);
                }
            }
        }

        return sprite.getWidth();
    }

    public void clear() {
        Arrays.fill(buffer, (byte) 0);
    }
}
