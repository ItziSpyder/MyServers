package me.improper.ipearlpvp.events;

import me.improper.ipearlpvp.IPearlPvP;
import me.improper.ipearlpvp.game.ShopItem;
import me.improper.ipearlpvp.game.inventory.Shop;
import me.improper.ipearlpvp.game.players.Stats;
import me.improper.ipearlpvp.server.ServerSound;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OnInventory implements Listener {

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        try {
            Inventory inv = e.getClickedInventory();
            ItemStack itemStack = e.getCurrentItem();
            ShopItem item = new ShopItem(itemStack);
            Stats stats = new Stats(p);
            ServerSound error = new ServerSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT,10,10);
            ServerSound buy = new ServerSound(p.getLocation(), Sound.ITEM_DYE_USE,10,10);
            ServerSound click = new ServerSound(p.getLocation(), Sound.UI_BUTTON_CLICK,10,10);
            if (inv == null) return;

            if (inv.equals(Shop.SHOPMENU)) {
                e.setCancelled(true);
                if (inv.getType().equals(InventoryType.PLAYER)) return;
                if (item.getDisplayName().trim().equals("")) return;

                switch (item.getType()) {
                    case DIAMOND_SWORD -> {
                        p.openInventory(Shop.WEAPONS);
                        click.play(p);
                    }
                    case NETHERITE_CHESTPLATE -> {
                        p.openInventory(Shop.ARMOR);
                        click.play(p);
                    }
                    case GOLDEN_APPLE -> {
                        p.openInventory(Shop.MISC);
                        click.play(p);
                    }
                }
            }
            else if (inv.equals(Shop.ARMOR) || inv.equals(Shop.WEAPONS) || inv.equals(Shop.MISC)) {
                e.setCancelled(true);
                if (inv.getType().equals(InventoryType.PLAYER)) return;
                if (item.getDisplayName().trim().equals("")) return;
                if (item.getDisplayName().contains(ChatColor.WHITE + "<---- Back")) {
                    p.openInventory(Shop.SHOPMENU);
                    click.play(p);
                }
                else {
                    int price = Integer.parseInt(item.getLore().get(item.getLore().size() - 1).replaceAll(ChatColor.GRAY + "Price: " + ChatColor.GREEN,""));
                    item.setPrice(price);
                    if (p.getInventory().firstEmpty() == -1) {
                        p.closeInventory();
                        p.sendMessage(IPearlPvP.STARTER + ChatColor.RED + "You don't have enough space!");
                        error.play(p);
                        return;
                    }
                    if (price > stats.getBalance()) {
                        p.closeInventory();
                        p.sendMessage(IPearlPvP.STARTER +
                                ChatColor.RED + "You don't have enough money! " +
                                ChatColor.GRAY + "Current Balance: " +
                                ChatColor.GREEN + stats.getStringBal());
                        error.play(p);
                        return;
                    }
                    p.sendMessage(IPearlPvP.STARTER +
                            ChatColor.GREEN + "You've purchased " +
                            ChatColor.GOLD + "x" + item.getAmount() + " " + item.getType().name().toLowerCase() + " " +
                            ChatColor.GREEN + "for " +
                            ChatColor.GOLD + price);
                    p.getInventory().addItem(item.toProduct());
                    stats.setBalance(stats.getBalance() - price);
                    stats.save();
                    buy.play(p);
                }
            }
        } catch (Exception exception) {}
    }
}
