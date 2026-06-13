package com.andezion.firstplugin.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class RankManager {

    public static final String PERM_ADMIN   = "firstplugin.admin";
    public static final String PERM_PREMIUM = "firstplugin.premium";

    private final Team adminTeam;
    private final Team premiumTeam;
    private final Team playerTeam;

    public RankManager() {
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

        adminTeam   = setupTeam(board, "fp_admin",   NamedTextColor.RED,   "[Admin] ");
        premiumTeam = setupTeam(board, "fp_premium",  NamedTextColor.GOLD,  "[Premium] ");
        playerTeam  = setupTeam(board, "fp_player",   NamedTextColor.WHITE, "[Player] ");
    }

    public void applyRank(Player player) {
        removeFromAll(player.getName());

        if (player.hasPermission(PERM_ADMIN)) {
            adminTeam.addEntry(player.getName());
        } else if (player.hasPermission(PERM_PREMIUM)) {
            premiumTeam.addEntry(player.getName());
        } else {
            playerTeam.addEntry(player.getName());
        }
    }

    private Team setupTeam(Scoreboard board, String name, NamedTextColor color, String prefixText) {
        Team team = board.getTeam(name);
        if (team == null) {
            team = board.registerNewTeam(name);
        }
        team.color(color);
        team.prefix(Component.text(prefixText, color));
        return team;
    }

    private void removeFromAll(String playerName) {
        adminTeam.removeEntry(playerName);
        premiumTeam.removeEntry(playerName);
        playerTeam.removeEntry(playerName);
    }
}
