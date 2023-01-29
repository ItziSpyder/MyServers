package me.improper.ipearlpvp.commands;

import me.improper.ipearlpvp.IPearlPvP;
import me.improper.ipearlpvp.game.displays.Displays;
import me.improper.ipearlpvp.game.inventory.Shop;
import me.improper.ipearlpvp.game.players.Stats;
import me.improper.ipearlpvp.server.ServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase().trim();

        try {
            switch (commandName) {
                case "test" -> {
                    Player p = (Player) sender;
                    Displays.sprinkle(p.getLocation());
                    return true;
                }
                case "shop" -> {
                    Player p = (Player) sender;
                    p.openInventory(Shop.SHOPMENU);
                }
                case "bal" -> {
                    OfflinePlayer p = (OfflinePlayer) sender;
                    p = args.length == 0 ? p : Bukkit.getOfflinePlayer(args[0]);
                    Stats stats = new Stats(p);
                    sender.sendMessage(IPearlPvP.STARTER +
                            ChatColor.YELLOW + p.getName() + "'s " +
                            ChatColor.GOLD + "balance is " +
                            ChatColor.GREEN + stats.getStringBal());
                }
                case "stats" -> {
                    OfflinePlayer p = (OfflinePlayer) sender;
                    p = args.length == 0 ? p : Bukkit.getOfflinePlayer(args[0]);
                    Stats stats = new Stats(p);
                    StringBuilder builder = new StringBuilder();
                    builder.append(ChatColor.GOLD + "-------------------------\n")
                            .append("   ").append(ChatColor.GOLD + "Player: " + ChatColor.YELLOW + p.getName() + "\n")
                            .append("   ").append(ChatColor.GOLD + "Balance: " + ChatColor.GREEN + stats.getStringBal() + "\n")
                            .append("   ").append(ChatColor.GOLD + "KDR: " + ChatColor.YELLOW + stats.getKdr() + "\n")
                            .append("   ").append(ChatColor.GOLD + "Kill streak: " + ChatColor.YELLOW + stats.getKillStreak() + "\n")
                            .append("   ").append(ChatColor.GOLD + "Kills: " + ChatColor.YELLOW + stats.getKills() + "\n")
                            .append("   ").append(ChatColor.GOLD + "Deaths: " + ChatColor.YELLOW + stats.getDeaths() + "\n")
                            .append(ChatColor.GOLD + "-------------------------\n");
                    sender.sendMessage(builder.toString());
                }
                case "addbal" -> {
                    Player p = Bukkit.getPlayer(args[0]);
                    double amount = Double.parseDouble(args[1]);
                    Stats stats = new Stats(p);
                    stats.setBalance(stats.getBalance() + amount);
                    stats.save();
                    sender.sendMessage(IPearlPvP.STARTER +
                            ChatColor.YELLOW + p.getName() + "'s " +
                            ChatColor.GOLD + "balance is now " +
                            ChatColor.GREEN + stats.getStringBal());
                }
                case "setbal" -> {
                    Player p = Bukkit.getPlayer(args[0]);
                    double amount = Double.parseDouble(args[1]);
                    Stats stats = new Stats(p);
                    stats.setBalance(amount);
                    stats.save();
                    sender.sendMessage(IPearlPvP.STARTER +
                            ChatColor.YELLOW + p.getName() + "'s " +
                            ChatColor.GOLD + "balance is now " +
                            ChatColor.GREEN + stats.getStringBal());
                }
                case "togglemapreset" -> {
                    ServerUtils.MAPRESET.RESUME = !ServerUtils.MAPRESET.RESUME;
                    sender.sendMessage(IPearlPvP.STARTER +
                            ChatColor.LIGHT_PURPLE + "Map reset timer is now set to: " +
                            ChatColor.GRAY + ServerUtils.MAPRESET.RESUME);
                }
                case "repair" -> {
                    Player p = (Player) sender;
                    Stats stats = new Stats(p);
                    if (400 > stats.getBalance()) {
                        sender.sendMessage(IPearlPvP.STARTER +
                                ChatColor.RED + "You cannot afford this! " +
                                ChatColor.GRAY + "Current Balance: " +
                                ChatColor.GREEN + stats.getStringBal());
                        return true;
                    }
                    List<String> items = new ArrayList<>();
                    for (ItemStack item : p.getInventory().getContents()) {
                        try {
                            if (item != null && hasDura(item)) {
                                ItemStack newItem = new ItemStack(item.getType());
                                item.setDurability(newItem.getDurability());
                                items.add(item.getType().name().toLowerCase().trim());
                            }
                            item.setAmount(item.getMaxStackSize());
                        } catch (NullPointerException exception) {}
                    }
                    sender.sendMessage(IPearlPvP.STARTER +
                            ChatColor.GREEN + "Restocked items + Repaired the following: " +
                            ChatColor.GRAY + items);
                    stats.setBalance(stats.getBalance() - 400);
                    stats.save();
                }
                case "resetmap" -> ServerUtils.MAPRESET.resetMap();
            }
            return true;
        }
        catch (Exception exception) {
            String message = IPearlPvP.STARTER + ChatColor.RED + "Command Error: ";
            if (exception instanceof NullPointerException) message += "Message contains a null value!";
            else if (exception instanceof IndexOutOfBoundsException) message += "Incomplete command, please provide more information!";
            else message += exception.getMessage();
            sender.sendMessage(message);
            return true;
        }
    }

    static boolean hasDura(ItemStack item) {
        String name = item.getType().name();
        return name.contains("HELMET") ||
                name.contains("CHESTPLATE") ||
                name.contains("LEGGINGS") ||
                name.contains("BOOTS") ||
                name.contains("BOW") ||
                name.contains("SHIELD") ||
                name.contains("SWORD") ||
                name.contains("_AXE") ||
                name.contains("PICKAXE") ||
                name.contains("SHOVEL") ||
                name.contains("HOE") ||
                name.contains("ELYTRA");
    }
}
