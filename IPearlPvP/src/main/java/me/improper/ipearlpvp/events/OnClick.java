package me.improper.ipearlpvp.events;

import me.improper.ipearlpvp.IPearlPvP;
import me.improper.ipearlpvp.server.ServerSound;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class OnClick implements Listener {

    @EventHandler
    public static void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        try {
            ItemStack item = e.getItem();

            switch (item.getType()) {
                case EXPERIENCE_BOTTLE -> {
                    e.setCancelled(true);
                    new BukkitRunnable() {
                        int times = 0;
                        ServerSound toss = new ServerSound(p.getLocation(), Sound.ENTITY_SNOWBALL_THROW,1,0.1F);
                        @Override
                        public void run() {
                            if (times < 5 && item.getAmount() > 0) {
                                ThrownExpBottle exp = p.getWorld().spawn(p.getEyeLocation(),ThrownExpBottle.class);
                                exp.setShooter(p);
                                exp.setVelocity(p.getLocation().getDirection());
                                toss.playWithin(200);
                                if (!p.getGameMode().equals(GameMode.CREATIVE)) item.setAmount(item.getAmount() - 1);
                                times ++;
                            } else this.cancel();
                        }
                    }.runTaskTimer(IPearlPvP.getInstance(),0,1);
                }
            }
        } catch (Exception exception) {}
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public static void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        try {
            switch (b.getType()) {
                case TNT -> {
                    b.setType(Material.AIR);
                    TNTPrimed tnt = b.getWorld().spawn(b.getLocation().add(0.5,0,0.5),TNTPrimed.class);
                    tnt.setSource(p);
                }
            }
        } catch (Exception exception) {}
    }
}
