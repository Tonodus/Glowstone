package net.glowstone.map;

import net.glowstone.GlowWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnvilWorldMapManager implements AnvilShortBasedMapManager {
    private final GlowWorld world;
    private short lastId;
    private Map<Short, GlowMapView> idToMap = new HashMap<>();
    private List<GlowMapView> maps = new ArrayList<>();

    public AnvilWorldMapManager(GlowWorld world) {
        this.lastId = -1;
        this.world = world;
    }

    /////////////////////////////
    // lastId management

    @Override
    public final short getLastCreatedMapId() {
        return lastId;
    }

    @Override
    public final void incLastCreatedMap() {
        if (lastId == Short.MAX_VALUE) {
            throw new IllegalStateException("AnvilWorldMapManager can only handle " + lastId + " maps. Cannot inc lastId!");
        }

        lastId++;
    }

    @Override
    public final void setLastCreatedMap(short id) {
        lastId = id;
    }

    //////////////////////////////
    // maps loading / creating

    @Override
    public GlowMapView getMap(Short uid) throws IllegalArgumentException {
        GlowMapView view;

        view = idToMap.get(uid);

        if (view == null)
            throw new IllegalArgumentException("Map with id " + uid + " does not exists!");

        return view;
    }

    @Override
    public GlowMapView createNewMap() {
        GlowMapView newOne = new GlowMapView(world, (short) 0);
        addIgnoringId(newOne);
        return newOne;
    }

    @Override
    public GlowMapView createNewMap(Short uid) throws IllegalArgumentException {
        if (idToMap.get(uid) != null)
            throw new IllegalArgumentException("The uid " + uid + " is already in use!");

        GlowMapView newOne = new GlowMapView(world, uid);
        set(newOne);
        return newOne;
    }

    @Override
    public void clear() {
        this.maps.clear();
        this.idToMap.clear();
        this.lastId = -1;
    }

    @Override
    public void set(GlowMapView mapView) {
        Short id = mapView.getId();
        idToMap.put(id, mapView);
        maps.add(mapView);
        lastId = (short) Math.max(lastId, id);
    }

    @Override
    public void addIgnoringId(GlowMapView mapView) {
        incLastCreatedMap();
        mapView.setId(lastId);
        set(mapView);
    }

    @Override
    public Iterable<? extends GlowMapView> getAllMaps() {
        return maps;
    }
}
