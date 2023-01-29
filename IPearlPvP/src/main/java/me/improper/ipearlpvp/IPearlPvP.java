package me.improper.ipearlpvp;

import me.improper.ipearlpvp.commands.Commands;
import me.improper.ipearlpvp.commands.Tabs;
import me.improper.ipearlpvp.data.Config;
import me.improper.ipearlpvp.events.*;
import me.improper.ipearlpvp.game.inventory.Shop;
import me.improper.ipearlpvp.server.ServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public final class IPearlPvP extends JavaPlugin {

    public static String STARTER;

    @Override
    public void onEnable() {
        // Plugin startup logic
        STARTER = Config.PLUGIN.getPrefix();
        Shop.registerAll();

        // Events
        Bukkit.getPluginManager().registerEvents(new OnDeath(),this);
        Bukkit.getPluginManager().registerEvents(new OnJoin(),this);
        Bukkit.getPluginManager().registerEvents(new OnInventory(),this);
        Bukkit.getPluginManager().registerEvents(new OnDamage(),this);
        Bukkit.getPluginManager().registerEvents(new OnCommand(),this);

        // Files
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Commands
        getCommand("test").setExecutor(new Commands());
        getCommand("test").setTabCompleter(new Tabs());
        getCommand("shop").setExecutor(new Commands());
        getCommand("shop").setTabCompleter(new Tabs());
        getCommand("bal").setExecutor(new Commands());
        getCommand("bal").setTabCompleter(new Tabs());
        getCommand("stats").setExecutor(new Commands());
        getCommand("stats").setTabCompleter(new Tabs());
        getCommand("addbal").setExecutor(new Commands());
        getCommand("addbal").setTabCompleter(new Tabs());
        getCommand("setbal").setExecutor(new Commands());
        getCommand("setbal").setTabCompleter(new Tabs());
        getCommand("togglemapreset").setExecutor(new Commands());
        getCommand("togglemapreset").setTabCompleter(new Tabs());
        getCommand("repair").setExecutor(new Commands());
        getCommand("repair").setTabCompleter(new Tabs());
        getCommand("resetmap").setExecutor(new Commands());
        getCommand("resetmap").setTabCompleter(new Tabs());

        // Per second loop
        new BukkitRunnable() {
            @Override
            public void run() {
                OnDamage.COMBAT.checkCombat();
                ServerUtils.MAPRESET.checkTimer();
                ServerUtils.SCOREBOARD.checkLoop();
            }
        }.runTaskTimer(this,0,20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getInstance() {
        return Bukkit.getPluginManager().getPlugin("IPearlPvP");
    }

    public static File getStatistics() {
        File file = new File("plugins/IPearlPvP/statistics.yml");
        try {
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (Exception exception) {}
        return file;
    }
}
