package com.andezion.firstplugin.managers;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProtectionManager {

    private final JavaPlugin plugin;
    private final Set<String> protectedBlocks = new HashSet<>();
    private final Set<UUID> protectModeAdmins = new HashSet<>();
    private final File dataFile;

    public ProtectionManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "protected_blocks.txt");
        load();
    }

    public boolean isProtected(Location loc) {
        return protectedBlocks.contains(serialize(loc));
    }

    public void protect(Location loc) {
        protectedBlocks.add(serialize(loc));
        save();
    }

    public void unprotect(Location loc) {
        protectedBlocks.remove(serialize(loc));
        save();
    }

    public boolean toggleProtectMode(UUID playerId) {
        if (protectModeAdmins.remove(playerId)) return false;
        protectModeAdmins.add(playerId);
        return true;
    }

    public boolean isInProtectMode(UUID playerId) {
        return protectModeAdmins.contains(playerId);
    }

    private String serialize(Location loc) {
        return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }

    private void load() {
        if (!dataFile.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) protectedBlocks.add(line.trim());
            }
        } catch (IOException e) {
            plugin.getLogger().warning("Could not load protected blocks: " + e.getMessage());
        }
    }

    private void save() {
        plugin.getDataFolder().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (String entry : protectedBlocks) {
                writer.write(entry);
                writer.newLine();
            }
        } catch (IOException e) {
            plugin.getLogger().warning("Could not save protected blocks: " + e.getMessage());
        }
    }
}
