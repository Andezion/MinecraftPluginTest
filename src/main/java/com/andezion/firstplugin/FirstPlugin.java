package com.andezion.firstplugin;

import com.andezion.firstplugin.listeners.PlayerEventListener;
import org.bukkit.plugin.java.JavaPlugin;

public class FirstPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("FirstPlugin is now enabled!");
        getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("FirstPlugin has been disabled.");
    }
}
