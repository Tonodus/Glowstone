package net.glowstone.io.anvil;

import net.glowstone.GlowServer;
import net.glowstone.map.AnvilShortBasedMapManager;
import net.glowstone.map.GlowMapView;
import net.glowstone.util.nbt.CompoundTag;
import net.glowstone.util.nbt.InvalidNbtException;
import net.glowstone.util.nbt.NBTInputStream;
import org.bukkit.map.MapView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AnvilMapLoader {
    private final AnvilShortBasedMapManager mapManager;

    public AnvilMapLoader(AnvilShortBasedMapManager mapManager) {
        this.mapManager = mapManager;
    }

    public void load(File worldDataFolder) {
        //load last map id
        File idcounts = new File(worldDataFolder, "idcounts.dat");
        short lastId = loadIdcountsDat(idcounts);
        mapManager.setLastCreatedMap(lastId);

        //load all maps
        for (short mapId = 0; mapId <= lastId; mapId++) {
            loadMapFile(new File(worldDataFolder, "map_" + mapId + ".dat"), mapId);
        }
    }

    private short loadIdcountsDat(File idcounts) {
        short lastId = -1;

        try (NBTInputStream stream = new NBTInputStream(new FileInputStream(idcounts), false)) {
            CompoundTag main = stream.readCompound();
            if (main.isShort("map")) {
                lastId = main.getShort("map");
            } else {
                GlowServer.logger.warning("Found idcounts.dat with invalid entry map (" + idcounts + ")");
            }
        } catch (FileNotFoundException e) {
            //that's ok
        } catch (IOException e) {
            GlowServer.logger.warning("IOException while reading " + idcounts.getPath() + ". Cannot load maps!");
            e.printStackTrace();
        }

        return lastId;
    }

    private void loadMapFile(File mapFile, short mapId) {
        try (NBTInputStream stream = new NBTInputStream(new FileInputStream(mapFile), true)) {
            CompoundTag main = stream.readCompound();
            loadMapFromCompound(mapId, main);
            return;
        } catch (InvalidNbtException nbtex) {
            GlowServer.logger.warning("Invalid nbt syntax in map file: " + mapFile);
            nbtex.printStackTrace();
        } catch (FileNotFoundException e) {
            GlowServer.logger.warning("Didn't found map file " + mapFile.getPath() + "! Invalid lastId!");
        } catch (IOException e) {
            GlowServer.logger.warning("IOException while reading " + mapFile.getPath());
            e.printStackTrace();
        }

        //Create empty map, so that lasId isn't wrong
        GlowServer.logger.warning("Map could not be read, creating empty map for " + mapFile);
        mapManager.createNewMap(mapId);
    }

    private void loadMapFromCompound(short id, CompoundTag main) throws InvalidNbtException {
        if (!main.isCompound("data")) {
            throw new InvalidNbtException("Expect Compound 'data'");
        }
        CompoundTag data = main.getCompound("data");

        if (!data.isByteArray("colors")) {
            throw new InvalidNbtException("Expect ByteArray 'colors'");
        }
        byte[] colors = data.getByteArray("colors");

        if (!data.isByte("dimension")) {
            throw new InvalidNbtException("Expect byte 'dimension'");
        }
        byte dimension = data.getByte("dimension");

        //TODO
        short height = data.getShort("height"); //128
        byte scale = data.getByte("scale");
        short width = data.getShort("width"); //128
        int xCenter = data.getInt("xCenter");
        int zCenter = data.getInt("zCenter");

        makeMap(id, colors, scale, xCenter, zCenter);
    }

    private void makeMap(short id, byte[] colors, byte byteScale, int xCenter, int zCenter) {
        GlowMapView mapView = mapManager.createNewMap(id);
        mapView.setCenterX(xCenter);
        mapView.setCenterZ(zCenter);
        mapView.setScale(MapView.Scale.valueOf(byteScale));
        mapView.setBase(colors);
    }
}
