package com.example.vanish;

import java.net.http.WebSocket.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class VanishPlugin extends JavaPlugin implements Listener {

    private Scoreboard scoreboard;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        makePlayerVanish(player);
        updateScoreboard(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        makePlayerVisible(player);
        updateScoreboard(player);
    }

    private void makePlayerVanish(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.hidePlayer(this, player);
        }
        player.sendMessage(ChatColor.GREEN + "You are now vanished.");
    }

    private void makePlayerVisible(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(this, player);
        }
        player.sendMessage(ChatColor.GREEN + "You are now visible.");
    }

    private void updateScoreboard(Player player) {
        Team vanishTeam = scoreboard.getTeam("vanish");
        if (vanishTeam == null) {
            vanishTeam = scoreboard.registerNewTeam("vanish");
        }

        if (player.hasPermission("vanish.use")) {
            vanishTeam.addPlayer(player);
        } else {
            vanishTeam.removePlayer(player);
        }
    }
}