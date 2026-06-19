package com.andezion.firstplugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class KillCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("firstplugin.admin")) {
            sender.sendMessage(Component.text("You have to rights!", NamedTextColor.RED));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Component.text("Usage: /kill <name or UUID>", NamedTextColor.RED));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            try {
                UUID uuid = UUID.fromString(args[0]);
                target = Bukkit.getPlayer(uuid);
            } catch (IllegalArgumentException ignored) {}
        }

        if (target == null || !target.isOnline()) {
            sender.sendMessage(Component.text("Player '" + args[0] + "' not found or offline.", NamedTextColor.RED));
            return true;
        }

        target.setHealth(0);
        sender.sendMessage(Component.text("Player " + target.getName() + " has been killed.", NamedTextColor.GREEN));
        return true;
    }
}
