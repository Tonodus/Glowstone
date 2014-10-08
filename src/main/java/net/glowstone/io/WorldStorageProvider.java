package net.glowstone.io;

import net.glowstone.GlowWorld;

import java.io.File;

/**
 * Interface for providers of world data storage, including chunks and various
 * metadata.
 */
public interface WorldStorageProvider {

    /**
     * Initialize the storage to correspond to the given world.
     *
     * @param world The world to use.
     */
    public void setWorld(GlowWorld world);

    /**
     * Get the folder holding the world data, if the filesystem is being used.
     *
     * @return The world folder, or null.
     */
    public File getFolder();

    /**
     * Get the {@link net.glowstone.io.ChunkIoService} for this world, to be used for reading
     * and writing chunk data.
     *
     * @return The {@link net.glowstone.io.ChunkIoService}.
     */
    public ChunkIoService getChunkIoService();

    /**
     * Get the {@link net.glowstone.io.WorldMetadataService} for this world, to be used for
     * reading and writing world metadata (seed, time, so on).
     *
     * @return The {@link net.glowstone.io.WorldMetadataService}.
     */
    public WorldMetadataService getMetadataService();

    /**
     * Get the {@link net.glowstone.io.PlayerDataService} for this world, to be used for
     * reading and writing data for online and offline players.
     *
     * @return The {@link net.glowstone.io.PlayerDataService}.
     */
    public PlayerDataService getPlayerDataService();

    /**
     * Gets the {@link net.glowstone.map.MapManager} for this world.
     *
     * @return The {@link net.glowstone.map.MapManager}
     */
    public MapIOService getMapIoService();

}
