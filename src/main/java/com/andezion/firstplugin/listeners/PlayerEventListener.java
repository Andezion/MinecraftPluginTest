package com.andezion.firstplugin.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.joinMessage(
                Component.text("Welcome to Andezion's custom server!", NamedTextColor.YELLOW)
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.quitMessage(
                Component.text("Goodbye, " + event.getPlayer().getName() + "!", NamedTextColor.GRAY)
        );
    }
}
