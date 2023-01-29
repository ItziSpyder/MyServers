package me.improper.ipearlpvp.events;

import me.improper.ipearlpvp.IPearlPvP;
import me.improper.ipearlpvp.data.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.UUID;

public class OnDamage implements Listener {

    public class COMBAT {
        private static HashMap<String,Long> COMBATLIST = new HashMap<>();
        public static boolean inCombat(Player player) {
            return COMBATLIST.containsKey(player.getName()) && COMBATLIST.get(player.getName()) > System.currentTimeMillis();
        }
        public static void removeCombat(Player player) {
            COMBATLIST.remove(player.getName());
        }
        public static void resetCombat(Player player) {
            if (!inCombat(player)) player.sendMessage(IPearlPvP.STARTER + ChatColor.RED + "Combat: You're now in combat!");
            COMBATLIST.put(player.getName(),System.currentTimeMillis() + (Config.GAMEPLAY.getCombatTimer() * 1000L));
        }
        public static double getTimerLeft(Player player) {
            return Math.floor((COMBATLIST.get(player.getName()) - System.currentTimeMillis()) / 10.0) / 100.0;
        }
        public static void checkCombat() {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (inCombat(p)) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(IPearlPvP.STARTER +
                            ChatColor.RED + "In Combat: " +
                            ChatColor.GOLD + getTimerLeft(p)));
                }
                else if (COMBATLIST.containsKey(p.getName())) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(IPearlPvP.STARTER +
                            ChatColor.RED + "Combat: " +
                            ChatColor.GREEN + "You're safe now!"));
                    p.sendMessage(IPearlPvP.STARTER +
                            ChatColor.RED + "Combat: " +
                            ChatColor.GREEN + "You're safe now!");
                    removeCombat(p);
                }
            }
        }
    }

    private static HashMap<UUID,UUID> DAMAGELOG = new HashMap<>();

    @EventHandler
    public static void onPlayerDamage(EntityDamageByEntityEvent e) {
        double damage = e.getDamage();
        Entity victim = e.getEntity();
        Entity damager = e.getDamager();

        try {
            if (victim instanceof Player victimP && damager instanceof Player damagerP) {
                COMBAT.resetCombat(victimP);
                COMBAT.resetCombat(damagerP);
            }
            if (victim instanceof LivingEntity && damager instanceof EnderCrystal) {
                Entity root = Bukkit.getEntity(DAMAGELOG.get(damager.getUniqueId()));
                if (victim.getUniqueId().equals(root.getUniqueId())) return;
                e.setCancelled(true);
                ((LivingEntity) victim).damage(damage,root);
            }
            else if (victim instanceof EnderCrystal && damager instanceof Player) {
                DAMAGELOG.put(victim.getUniqueId(),damager.getUniqueId());
            }
        } catch (Exception exception) {}
    }
}
