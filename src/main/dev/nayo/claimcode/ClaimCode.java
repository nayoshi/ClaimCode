package dev.nayo.claimcode;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ClaimCode extends JavaPlugin {
    private static ClaimCode instance = null;

    public void onEnable() {
        instance = this;
        ensureOrCreateFolder(getDataFolder());

    }
    public static ClaimCode getInstance() {
        return instance;
    }

    private boolean ensureOrCreateFolder(File directory) {
        if (!directory.exists()) {
            return directory.mkdir();
        }
        return directory.isDirectory();
    }
    public void onDisable() {

    }
}
