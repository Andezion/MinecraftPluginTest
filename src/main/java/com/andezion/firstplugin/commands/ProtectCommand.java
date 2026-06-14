package com.andezion.firstplugin.commands;

import com.andezion.firstplugin.managers.ProtectionManager;
import com.andezion.firstplugin.managers.RankManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProtectCommand implements CommandExecutor {

    private final ProtectionManager protectionManager;

    public ProtectCommand(ProtectionManager protectionManager) {
        this.protectionManager = protectionManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (!player.hasPermission(RankManager.PERM_ADMIN)) {
            player.sendMessage(Component.text("You don't have permission.", NamedTextColor.RED));
            return true;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("toggle")) {
            boolean enabled = protectionManager.toggleProtectMode(player.getUniqueId());
            player.sendMessage(Component.text(
                    "Protect mode " + (enabled ? "ENABLED - all blocks you place will be protected." : "DISABLED."),
                    enabled ? NamedTextColor.GREEN : NamedTextColor.YELLOW
            ));
            return true;
        }

        if (args[0].equalsIgnoreCase("here")) {
            Block target = player.getTargetBlockExact(5);
            if (target == null) {
                player.sendMessage(Component.text("Look at a block first.", NamedTextColor.RED));
                return true;
            }
            protectionManager.protect(target.getLocation());
            player.sendMessage(Component.text("Block protected!", NamedTextColor.GREEN));
            return true;
        }

        if (args[0].equalsIgnoreCase("remove")) {
            Block target = player.getTargetBlockExact(5);
            if (target == null) {
                player.sendMessage(Component.text("Look at a block first.", NamedTextColor.RED));
                return true;
            }
            protectionManager.unprotect(target.getLocation());
            player.sendMessage(Component.text("Block unprotected!", NamedTextColor.YELLOW));
            return true;
        }

        player.sendMessage(Component.text("Usage: /protect [toggle|here|remove]", NamedTextColor.YELLOW));
        return true;
    }
}
