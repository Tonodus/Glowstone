package net.glowstone.map;

public interface AnvilShortBasedMapManager extends MapManager<Short> {
    /**
     * Gets the id of the last created map associated with this MapManager or -1 if this MapManager hasn't create any maps yet.
     *
     * @return The id of the last created map or -1
     */
    public short getLastCreatedMapId();

    /**
     * Increases the id of the last created map associated with this MapManager
     *
     * @see #setLastCreatedMap(short)
     */
    public void incLastCreatedMap();

    /**
     * Sets the id of the last created map associated with this MapManager
     *
     * @param id The new id
     * @see #incLastCreatedMap()
     */
    public void setLastCreatedMap(short id);
}
