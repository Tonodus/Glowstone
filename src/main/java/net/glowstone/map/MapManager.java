package net.glowstone.map;

/**
 * Class for easy map management.
 *
 * @param <I> The identifier of maps
 */
public interface MapManager<I> {
    /**
     * Gets the map with the given uid or loads it if necessary.
     *
     * @param uid The id of the map
     * @return The MapView for that map
     * @throws IllegalArgumentException if a map with the given uid does not exists
     */
    public GlowMapView getMap(I uid) throws IllegalArgumentException;

    /**
     * Creates a new map and automatically adds it to this manager.
     *
     * @return The new map
     */
    public GlowMapView createNewMap();

    /**
     * Creates a new map with the given identifier and automatically adds it to this manager.
     *
     * @param uid The identifier of the new map
     * @return The new map
     * @throws java.lang.IllegalArgumentException if the identifier is already in use
     */
    public GlowMapView createNewMap(I uid) throws IllegalArgumentException;

    /**
     * Deletes all references to maps that were associated with this manager.
     */
    public void clear();

    /**
     * Adds the {@link net.glowstone.map.GlowMapView} to this manager, using the id the map provides. May replace former map.
     *
     * @param mapView The {@link net.glowstone.map.GlowMapView} to add to this manager
     */
    public void set(GlowMapView mapView);

    /**
     * Adds this {@link net.glowstone.map.GlowMapView} to this manager, ignoring the mapView's uid.
     *
     * @param mapView The {@link net.glowstone.map.GlowMapView} to add
     */
    public void addIgnoringId(GlowMapView mapView);

    /**
     * Gets all the maps associated with this manager.
     *
     * @return An {@link java.lang.Iterable} over all the maps associated with this manager
     */
    public Iterable<? extends GlowMapView> getAllMaps();
}
