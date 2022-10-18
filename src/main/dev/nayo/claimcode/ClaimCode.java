package dev.nayo.claimcode;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public class ClaimCode extends JavaPlugin {
    private static ClaimCode instance = null;
    private String prefix;
    private UseDatabase db;
    private Helper helper;
    private boolean disabled;
    private CodeManager codeManager;
    public void onEnable() {
        instance = this;
        ensureOrCreateFolder(getDataFolder());
        saveDefaultConfig();
        try {
            UseDatabase db = new UseDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        reload();

    }

    public void reload() {
        reloadConfig();
        disabled = false;
        prefix = getConfig().getString("prefix");
        helper = new Helper();
        codeManager = new CodeManager();
    }


    private boolean ensureOrCreateFolder(File directory) {
        if (!directory.exists()) {
            return directory.mkdir();
        }
        return directory.isDirectory();
    }

    public void onDisable() {

    }
    public void toggleDisabled() {
        disabled = !disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public static ClaimCode getInstance() {
        return instance;
    }

    public Helper getHelper() {
        return helper;
    }

    public String getPrefix() {
        return prefix;
    }
}
