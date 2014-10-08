package net.glowstone.map;

import net.glowstone.GlowWorld;
import net.glowstone.entity.GlowPlayer;
import org.bukkit.World;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a map item.
 */
public final class GlowMapView implements MapView {

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

    public GlowMapView(GlowWorld world, short id, byte[] base) {
        this(world, id);
        MapRenderData data = new MapRenderData();
        data.fill(base);
        renderCache.put(null, data);
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

    ///////////////////////////////////////////
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
