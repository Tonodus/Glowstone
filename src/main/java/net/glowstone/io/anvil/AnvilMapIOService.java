package net.glowstone.io.anvil;

import net.glowstone.io.MapIOService;
import net.glowstone.map.AnvilShortBasedMapManager;
import net.glowstone.map.MapManager;

import java.io.File;

public class AnvilMapIOService implements MapIOService<Short> {
    private final File folder;

    /**
     * Creates a new AnvilMapIOService to load/save maps im Nbt/Anvil-Format.
     *
     * @param folder The folder containing the idcounts.dat and the map_XXX.dat files
     */
    public AnvilMapIOService(File folder) {
        this.folder = folder;
    }

    @Override
    public void save(MapManager<Short> mapManager) {
        if (!(mapManager instanceof AnvilShortBasedMapManager)) {
            throw new IllegalArgumentException("AnvilMapIOService can only save AnvilShortBasedMapManagers, got " + mapManager.getClass().getName());
        }

        AnvilMapSaver saver = new AnvilMapSaver((AnvilShortBasedMapManager) mapManager);
        saver.save(folder);
    }

    @Override
    public void load(MapManager<Short> mapManager) {
        if (!(mapManager instanceof AnvilShortBasedMapManager)) {
            throw new IllegalArgumentException("AnvilMapIOService can only load AnvilShortBasedMapManagers, got " + mapManager.getClass().getName());
        }

        AnvilMapLoader loader = new AnvilMapLoader((AnvilShortBasedMapManager) mapManager);
        loader.load(folder);
    }
}
