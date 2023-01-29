package me.improper.ipearlpvp.events;

import me.improper.ipearlpvp.IPearlPvP;
import me.improper.ipearlpvp.data.Config;
import me.improper.ipearlpvp.game.displays.Displays;
import me.improper.ipearlpvp.game.players.Stats;
import me.improper.ipearlpvp.server.ServerSound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnDeath implements Listener {

    @EventHandler
    public static void PlayerDeathEvent(PlayerDeathEvent e) {
        try {
            Player p = e.getEntity().getPlayer();
            Player k = e.getEntity().getKiller();
            OnDamage.COMBAT.removeCombat(p);

            if (k == null) {
                e.setDeathMessage(Config.MESSAGES.getNullDeath()
                        .replaceAll("%player%",p.getName()));
            }
            else {
                OnDamage.COMBAT.removeCombat(k);
                e.setDeathMessage(Config.MESSAGES.getDeath()
                        .replaceAll("%player%",p.getName())
                        .replaceAll("%killer%",k.getName()));
                Stats stats = new Stats(k);
                stats.setKills(stats.getKills() + 1);
                stats.setBalance(stats.getBalance() + 200);
                stats.setKillStreak(stats.getKillStreak() + 1);
                stats.save();

                if (stats.getKillStreak() >= 3) {
                    Bukkit.broadcastMessage(IPearlPvP.STARTER +
                            ChatColor.YELLOW + k.getName() + " " +
                            ChatColor.GOLD + "is now on a kill streak of " +
                            ChatColor.YELLOW + stats.getKillStreak());
                    k.sendTitle(ChatColor.GOLD + "" + ChatColor.ITALIC + "Kill Streak!",ChatColor.YELLOW + "x" + stats.getKillStreak(),10,60,10);
                }

                ServerSound villager = new ServerSound(p.getLocation(), Sound.ENTITY_VILLAGER_DEATH,10,10);
                ServerSound glass = new ServerSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK,10,0.1F);
                ServerSound portal = new ServerSound(p.getLocation(), Sound.BLOCK_PORTAL_TRAVEL,10,1);
                villager.playWithinAt(500);
                glass.playWithinAt(500);
                Bukkit.getScheduler().scheduleSyncDelayedTask(IPearlPvP.getInstance(),() -> {
                    portal.play(p);
                    String[] title = Config.MESSAGES.getDeathTitles().split("->");
                    p.sendTitle(title[0].trim(),title[1].trim(),10,60,10);
                },10);
            }
            Stats stats = new Stats(p);
            stats.setDeaths(stats.getDeaths() + 1);
            stats.setBalance(stats.getBalance() + 50);
            stats.setKillStreak(0);
            stats.save();
            Displays.sprinkle(p.getLocation());
        } catch (Exception exception) {}
    }
}
