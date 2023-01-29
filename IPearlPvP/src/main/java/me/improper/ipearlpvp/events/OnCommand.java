package me.improper.ipearlpvp.events;

import me.improper.ipearlpvp.IPearlPvP;
import me.improper.ipearlpvp.data.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OnCommand implements Listener {

    @EventHandler
    public static void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        String cmd = msg.split(" ")[0];

        if (OnDamage.COMBAT.inCombat(p) && Config.GAMEPLAY.getCombatBlacklist().contains(cmd)) {
            e.setCancelled(true);
            p.sendMessage(IPearlPvP.STARTER +
                    ChatColor.RED + "This command is blocked in combat! Remaining: " +
                    ChatColor.GOLD + OnDamage.COMBAT.getTimerLeft(p));
            return;
        }
    }
}
