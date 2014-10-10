package net.glowstone.map;

import net.glowstone.GlowWorld;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a map item.
 */
public final class GlowMapView implements MapView {
    private static final int MAP_SIZE = GlowMapCanvas.MAP_SIZE;

    private final Map<GlowPlayer, MapRenderData> renderCache = new HashMap<>();
    private final List<MapRenderer> renderers = new ArrayList<>();
    private final Map<MapRenderer, Map<GlowPlayer, GlowMapCanvas>> canvases = new HashMap<>();
    private short id;
    private Scale scale;
    private int x, z;
    private GlowWorld world;

    public GlowMapView(GlowWorld world, short id) {
        this.world = world;
        this.id = id;
        this.x = world.getSpawnLocation().getBlockX();
        this.z = world.getSpawnLocation().getBlockZ();
        this.scale = Scale.FAR;
        addRenderer(new GlowMapRenderer(this));
    }

    public void setBase(byte[] base) {
        if (isVirtual()) return;

        //Get the GlowMapRender canvas-map
        Map<GlowPlayer, GlowMapCanvas> canvasMap = canvases.get(renderers.get(0));
        if (canvasMap == null) {
            canvasMap = new HashMap<>();
            canvases.put(renderers.get(0), canvasMap);
        }

        //Get the canvas for ever player (null)
        GlowMapCanvas canvas = canvasMap.get(null);
        if (canvas == null) {
            canvas = new GlowMapCanvas(this);
            canvasMap.put(null, canvas);
        }

        //Set the base
        canvas.setBase(base);
    }

    public byte[] getBase() {
        MapRenderData renderData = renderCache.get(null);
        if (renderData != null) {
            return renderData.getBuffer();
        }

        return null;
    }
    //////////////////////////////////////////
    // Rendering

    private boolean isContextual() {
        for (MapRenderer renderer : renderers)
            if (renderer.isContextual())
                return true;

        return false;
    }

    public MapRenderData render(GlowPlayer player) {
        boolean isContextual = isContextual();
        GlowPlayer playerOrNull = isContextual ? player : null;

        MapRenderData data = renderCache.get(playerOrNull);

        if (data == null) {
            data = new MapRenderData();
            renderCache.put(playerOrNull, data);
        }

        if (isContextual && renderCache.containsKey(null))
            renderCache.remove(null);

        data.clear();
        for (MapRenderer renderer : renderers) {
            GlowMapCanvas canvas = canvases.get(renderer).get(playerOrNull);
            if (canvas == null) {
                canvas = new GlowMapCanvas(this);
                canvases.get(renderer).put(playerOrNull, canvas);
            }

            data.render(renderer, canvas, player);
        }

        return data;
    }

    public byte[] getScaledLocation(Location location) {
        return getScaledLocation(location.getBlockX(), location.getBlockZ());
    }

    public byte[] getScaledLocation(Vector vector) {
        return getScaledLocation(vector.getBlockX(), vector.getBlockZ());
    }

    private byte[] getScaledLocation(int x, int z) {
        int offX = x - this.x, offZ = z - this.z;

        int div = getDivForScale(scale);
        offX /= div;
        offZ /= div;


        offX += MAP_SIZE / 2;
        offZ += MAP_SIZE / 2;
        if (offX >= 0 && offX < MAP_SIZE && offZ >= 0 && offZ < MAP_SIZE) {
            return new byte[]{(byte) offX, (byte) offZ};
        } else {
            return new byte[0];
        }
    }

    private static int getDivForScale(MapView.Scale scale) {
        switch (scale) {
            case CLOSEST:
                return 1;
            case CLOSE:
                return 2;
            case NORMAL:
                return 4;
            case FAR:
                return 8;
            case FARTHEST:
                return 16;
        }

        throw new IllegalArgumentException("Invalid scale " + scale);
    }

    public boolean is(ItemStack item) {
        return item.getType() == Material.MAP && item.getDurability() == id;
    }

    ///////////////////////////////////////////
    //

    @Override
    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Override
    public boolean isVirtual() {
        if (renderers.size() > 0) {
            return !(renderers.get(0) instanceof GlowMapRenderer);
        }

        return false;
    }

    @Override
    public Scale getScale() {
        return scale;
    }

    @Override
    public void setScale(Scale scale) {
        if (scale == null) {
            throw new NullPointerException();
        }
        this.scale = scale;
    }

    @Override
    public int getCenterX() {
        return x;
    }

    @Override
    public int getCenterZ() {
        return z;
    }

    @Override
    public void setCenterX(int x) {
        this.x = x;
    }

    @Override
    public void setCenterZ(int z) {
        this.z = z;
    }

    @Override
    public GlowWorld getWorld() {
        return world;
    }

    @Override
    public void setWorld(World world) {
        this.world = (GlowWorld) world;
    }

    @Override
    public List<MapRenderer> getRenderers() {
        return renderers;
    }

    @Override
    public void addRenderer(MapRenderer renderer) {
        if (!renderers.contains(renderer)) {
            renderers.add(renderer);
            canvases.put(renderer, new HashMap<GlowPlayer, GlowMapCanvas>());
            renderer.initialize(this);
        }
    }

    @Override
    public boolean removeRenderer(MapRenderer renderer) {
        if (renderers.contains(renderer)) {
            renderers.remove(renderer);
            canvases.remove(renderer);
            return true;
        } else {
            return false;
        }
    }

}
