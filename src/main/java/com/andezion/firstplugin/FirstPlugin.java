package com.andezion.firstplugin;

import com.andezion.firstplugin.listeners.PlayerEventListener;
import com.andezion.firstplugin.managers.RankManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FirstPlugin extends JavaPlugin {

    private RankManager rankManager;

    @Override
    public void onEnable() {
        getLogger().info("FirstPlugin is now enabled!");

        rankManager = new RankManager();

        getServer().getPluginManager().registerEvents(
                new PlayerEventListener(rankManager), this
        );
    }

    @Override
    public void onDisable() {
        getLogger().info("FirstPlugin has been disabled.");
    }

    public RankManager getRankManager() {
        return rankManager;
    }
}
