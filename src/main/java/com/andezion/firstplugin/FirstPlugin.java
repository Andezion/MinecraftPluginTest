package com.andezion.firstplugin;

import com.andezion.firstplugin.commands.KillCommand;
import com.andezion.firstplugin.commands.ProtectCommand;
import com.andezion.firstplugin.listeners.BlockProtectionListener;
import com.andezion.firstplugin.listeners.PlayerEventListener;
import com.andezion.firstplugin.managers.ProtectionManager;
import com.andezion.firstplugin.managers.RankManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FirstPlugin extends JavaPlugin {

    private RankManager rankManager;
    private ProtectionManager protectionManager;

    @Override
    public void onEnable() {
        getLogger().info("FirstPlugin is now enabled!");

        rankManager = new RankManager();
        protectionManager = new ProtectionManager(this);

        getServer().getPluginManager().registerEvents(new PlayerEventListener(rankManager), this);
        getServer().getPluginManager().registerEvents(new BlockProtectionListener(protectionManager), this);

        getCommand("protect").setExecutor(new ProtectCommand(protectionManager));
        getCommand("kill").setExecutor(new KillCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("FirstPlugin has been disabled.");
    }

    public RankManager getRankManager() {
        return rankManager;
    }
}
