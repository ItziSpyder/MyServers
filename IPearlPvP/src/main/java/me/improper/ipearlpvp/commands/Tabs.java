package me.improper.ipearlpvp.commands;

import me.improper.ipearlpvp.server.ServerUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Tabs implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        String commandName = command.getName().toLowerCase().trim();

        switch (commandName) {
            case "test","shop","togglemapreset","repair","resetmap" -> {}
            case "bal","stats" -> {
                switch (args.length) {
                    case 1 -> list.addAll(ServerUtils.listPlayers());
                }
            }
            case "addbal","setbal" -> {
                switch (args.length) {
                    case 1 -> list.addAll(ServerUtils.listPlayers());
                    case 2 -> list.add(ChatColor.GRAY + "<amount>");
                }
            }
        }

        list.removeIf(i -> !i.toLowerCase().contains(args[args.length - 1].toLowerCase()));
        return list;
    }
}
