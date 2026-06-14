package com.andezion.firstplugin.listeners;

import com.andezion.firstplugin.managers.ProtectionManager;
import com.andezion.firstplugin.managers.RankManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockProtectionListener implements Listener {

    private final ProtectionManager protectionManager;

    public BlockProtectionListener(ProtectionManager protectionManager) {
        this.protectionManager = protectionManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!protectionManager.isProtected(event.getBlock().getLocation())) return;

        if (event.getPlayer().hasPermission(RankManager.PERM_ADMIN)) {
            protectionManager.unprotect(event.getBlock().getLocation());
            return;
        }

        event.setCancelled(true);
        event.getPlayer().sendMessage(Component.text("This block is protected!", NamedTextColor.RED));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission(RankManager.PERM_ADMIN)) return;
        if (!protectionManager.isInProtectMode(player.getUniqueId())) return;
        protectionManager.protect(event.getBlockPlaced().getLocation());
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().removeIf(block -> protectionManager.isProtected(block.getLocation()));
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().removeIf(block -> protectionManager.isProtected(block.getLocation()));
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        boolean hasProtected = event.getBlocks().stream()
                .anyMatch(block -> protectionManager.isProtected(block.getLocation()));
        if (hasProtected) event.setCancelled(true);
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        boolean hasProtected = event.getBlocks().stream()
                .anyMatch(block -> protectionManager.isProtected(block.getLocation()));
        if (hasProtected) event.setCancelled(true);
    }
}
