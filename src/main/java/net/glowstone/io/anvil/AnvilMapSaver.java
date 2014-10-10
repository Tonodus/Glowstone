package net.glowstone.io.anvil;

import net.glowstone.GlowServer;
import net.glowstone.map.AnvilShortBasedMapManager;
import net.glowstone.map.GlowMapView;
import net.glowstone.util.nbt.CompoundTag;
import net.glowstone.util.nbt.NBTOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AnvilMapSaver {
    private final AnvilShortBasedMapManager mapManager;

    public AnvilMapSaver(AnvilShortBasedMapManager mapManager) {
        this.mapManager = mapManager;
    }

    public void save(File worldDataFolder) {
        if (mapManager.getLastCreatedMapId() < 0) return;

        saveIdCounts(new File(worldDataFolder, "idcounts.dat"));

        for (GlowMapView map : mapManager.getAllMaps()) {
            File mapFile = new File(worldDataFolder, "map_" + map.getId() + ".dat");
            saveMap(map, mapFile);
        }
    }

    ///////////////////////////////
    // map_XXX.dat

    private void saveMap(GlowMapView map, File mapFile) {
        if (!mapFile.exists()) {
            try {
                mapFile.createNewFile();
            } catch (IOException e) {
                GlowServer.logger.warning("IOException while creating " + mapFile.getPath() + ". This map will be lost!");
                e.printStackTrace();
                return;
            }
        }

        try (NBTOutputStream out = new NBTOutputStream(new FileOutputStream(mapFile), true)) {
            CompoundTag main = new CompoundTag();
            saveMapToCompound(map, main);
            out.writeTag(main);
        } catch (FileNotFoundException e) {
            //Should not happen
            e.printStackTrace();
        } catch (IOException e) {
            GlowServer.logger.warning("IOException while saving map: " + mapFile.getPath());
            e.printStackTrace();
        }
    }

    private void saveMapToCompound(GlowMapView map, CompoundTag main) {
        CompoundTag data = new CompoundTag();
        byte[] colors = map.getBase();
        if (colors == null)
            data.putByteArray("colors", new byte[128 * 128]); //TODO: Empty array? No value?
        else
            data.putByteArray("colors", colors);
        data.putByte("dimension", 0); //TODO
        data.putShort("height", 128);
        data.putByte("scale", map.getScale().getValue());
        data.putShort("width", 128);
        data.putInt("xCenter", map.getCenterX());
        data.putInt("zCenter", map.getCenterZ());

        main.putCompound("data", data);
    }

    ////////////////////////////////////////////////
    // idcounts.dat

    private void saveIdCounts(File idCountsFile) {
        if (!idCountsFile.exists()) {
            try {
                idCountsFile.createNewFile();
            } catch (IOException e) {
                GlowServer.logger.warning("IOException while creating " + idCountsFile.getPath() + ". All maps are maybe lost!");
                e.printStackTrace();
                return;
            }
        }

        try (NBTOutputStream nbt = new NBTOutputStream(new FileOutputStream(idCountsFile), false)) {
            CompoundTag main = new CompoundTag();
            main.putShort("map", mapManager.getLastCreatedMapId());
            nbt.writeTag(main);
        } catch (FileNotFoundException e) {
            //Should not happen
            e.printStackTrace();
        } catch (IOException e) {
            GlowServer.logger.warning("IOException while saving " + idCountsFile.getPath());
            e.printStackTrace();
        }

    }
}
