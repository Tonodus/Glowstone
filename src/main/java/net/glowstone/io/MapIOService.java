package net.glowstone.io;

import net.glowstone.map.MapManager;

/**
 * Loads/Saves the maps from a {@link net.glowstone.map.MapManager}
 */
public interface MapIOService<I> {
    /**
     * Saves all the maps from the given {@link net.glowstone.map.MapManager}.
     *
     * @param mapManager The {@link net.glowstone.map.MapManager} containing the maps to save
     */
    public void save(MapManager<I> mapManager);

    /**
     * Loads all the maps into the given {@link net.glowstone.map.MapManager}.
     *
     * @param mapManager The {@link net.glowstone.map.MapManager} to load the maps into
     */
    public void load(MapManager<I> mapManager);
}
