package me.improper.ipearlpvp.game.players;

import me.improper.ipearlpvp.IPearlPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class Stats {

    private String player;
    private String uuid;
    private int kills;
    private int deaths;
    private int killStreak;
    private double balance;



    public Stats(Player player) {
        File file = IPearlPvP.getStatistics();
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        if (data.getConfigurationSection("stats." + player.getUniqueId()) == null) {
            this.player = player.getName();
            this.uuid = player.getUniqueId().toString();
            this.kills = 0;
            this.deaths = 0;
            this.killStreak = 0;
            this.balance = 0.0;
            player.sendMessage(IPearlPvP.STARTER + ChatColor.GOLD + "You've created a new statistics profile!");
            return;
        }
        this.player = data.getString("stats." + player.getUniqueId() + ".player");
        this.uuid = data.getString("stats." + player.getUniqueId() + ".uuid");
        this.kills = data.getInt("stats." + player.getUniqueId() + ".kills");
        this.deaths = data.getInt("stats." + player.getUniqueId() + ".deaths");
        this.killStreak = data.getInt("stats." + player.getUniqueId() + ".killStreak");
        this.balance = data.getDouble("stats." + player.getUniqueId() + ".balance");
        this.save();
    }

    public Stats(OfflinePlayer player) {
        File file = IPearlPvP.getStatistics();
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        if (data.getConfigurationSection("stats." + player.getUniqueId()) == null) {
            this.player = player.getName();
            this.uuid = player.getUniqueId().toString();
            this.kills = 0;
            this.deaths = 0;
            this.killStreak = 0;
            this.balance = 0.0;
            return;
        }
        this.player = data.getString("stats." + player.getUniqueId() + ".player");
        this.uuid = data.getString("stats." + player.getUniqueId() + ".uuid");
        this.kills = data.getInt("stats." + player.getUniqueId() + ".kills");
        this.deaths = data.getInt("stats." + player.getUniqueId() + ".deaths");
        this.killStreak = data.getInt("stats." + player.getUniqueId() + ".killStreak");
        this.balance = data.getDouble("stats." + player.getUniqueId() + ".balance");
    }

    public void save() {
        try {
            File file = IPearlPvP.getStatistics();
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            data.set("stats." + this.uuid + ".player",this.player);
            data.set("stats." + this.uuid + ".uuid",this.uuid);
            data.set("stats." + this.uuid + ".kills",this.kills);
            data.set("stats." + this.uuid + ".deaths",this.deaths);
            data.set("stats." + this.uuid + ".killStreak",this.killStreak);
            data.set("stats." + this.uuid + ".balance",this.balance);
            data.save(file);
        } catch (Exception exception) {}
    }

    public double getKdr() {
        return Math.floor(((double) kills / (double) deaths) * 100) / 100;
    }

    public String getStringBal() {
        String value = "$";
        if (this.balance >= 1000000000000.0) value += Math.floor(this.balance/10000000000.0) / 100.0 + "T";
        else if (this.balance >= 1000000000.0) value += Math.floor(this.balance/10000000.0) / 100.0 + "B";
        else if (this.balance >= 1000000.0) value += Math.floor(this.balance/10000.0) / 100.0 + "M";
        else if (this.balance >= 1000.0) value += Math.floor(this.balance/10.0) / 100.0 + "K";
        else value += String.valueOf(Math.floor(this.balance * 100.0) / 100.0);
        return value;
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public double getBalance() {
        return balance;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getKills() {
        return kills;
    }

    public int getKillStreak() {
        return killStreak;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(player);
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid.toString();
    }

    public void setKillStreak(int killStreak) {
        this.killStreak = killStreak;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
