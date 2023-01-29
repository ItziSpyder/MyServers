package me.improper.ipearlpvp.events;

import me.improper.ipearlpvp.IPearlPvP;
import me.improper.ipearlpvp.data.Config;
import me.improper.ipearlpvp.game.players.Stats;
import me.improper.ipearlpvp.server.ServerSound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnJoin implements Listener {

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.setGameMode(GameMode.SURVIVAL);

        e.setJoinMessage(Config.MESSAGES.getJoin()
                .replaceAll("%player%",p.getName()));
        for (String msg : Config.PLAYER.getJoinChatCommands()) p.chat(msg);
        ServerSound join = new ServerSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,10,0.1F);
        ServerSound tip = new ServerSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,10,0.1F);

        Stats stats = new Stats(p);
        stats.setBalance(stats.getBalance() + 100);
        if (!p.hasPlayedBefore()) stats.setBalance(stats.getBalance() + 20000);
        stats.save();

        Bukkit.getScheduler().scheduleSyncDelayedTask(IPearlPvP.getInstance(),() -> {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 200; i ++) builder.append("\n ");
            builder.append(ChatColor.GOLD + "-------------------------\n")
                    .append("   ").append(ChatColor.GOLD + "Player: " + ChatColor.YELLOW + p.getName() + "\n")
                    .append("   ").append(ChatColor.GOLD + "Balance: " + ChatColor.GREEN + stats.getStringBal() + ChatColor.RED + " -> /shop\n")
                    .append("   ").append(ChatColor.GOLD + "KDR: " + ChatColor.YELLOW + stats.getKdr() + "\n")
                    .append("   ").append(ChatColor.GOLD + "Kill streak: " + ChatColor.YELLOW + stats.getKillStreak() + "\n")
                    .append("   ").append(ChatColor.GOLD + "Unique joins: " + ChatColor.YELLOW + Bukkit.getOfflinePlayers().length + "\n ")
                    .append(ChatColor.GOLD + "-------------------------\n");
            p.sendMessage(builder.toString());
            join.play(p);
        },30);
        Bukkit.getScheduler().scheduleSyncDelayedTask(IPearlPvP.getInstance(),() -> {
            p.sendMessage(IPearlPvP.STARTER + ChatColor.YELLOW + "Ready? Kill players to get money! Use your current money to purchase gear!\n ");
            tip.play(p);
        },50);
        Bukkit.getScheduler().scheduleSyncDelayedTask(IPearlPvP.getInstance(),() -> {
            p.sendMessage(IPearlPvP.STARTER + ChatColor.YELLOW + "Jump in the pit to get started!\n ");
            tip.play(p);
        },70);
        Bukkit.getScheduler().scheduleSyncDelayedTask(IPearlPvP.getInstance(),() -> {
            p.sendMessage(IPearlPvP.STARTER + ChatColor.YELLOW + "Remember to use " + ChatColor.GOLD + "/shop! Enjoy your stay!\n ");
            tip.play(p);
        },90);
    }

    @EventHandler
    public static void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(Config.MESSAGES.getLeave()
                .replaceAll("%player%",p.getName()));
        if (OnDamage.COMBAT.inCombat(p)) {
            Bukkit.broadcastMessage(IPearlPvP.STARTER +
                    ChatColor.GOLD + p.getName() + " " +
                    ChatColor.RED + "combat logged while having " +
                    ChatColor.GOLD + OnDamage.COMBAT.getTimerLeft(p) + " " +
                    ChatColor.RED + "seconds left of combat!");
            p.setHealth(0);
            OnDamage.COMBAT.removeCombat(p);
        }
    }
}
