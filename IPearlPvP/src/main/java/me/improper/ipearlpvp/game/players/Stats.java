package me.improper.ipearlpvp.game.players;

import me.improper.ipearlpvp.IPearlPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.UUID;

public class Stats implements Serializable {

    private String player;
    private String uuid;
    private int kills;
    private int deaths;
    private int killStreak;
    private double balance;

    public static final long serializationId = 53283642412313131L;


    public Stats(Player player) {
        File file = new File("plugins/IPearlPvP/stats/" + player.getUniqueId() + ".stats");
        if (!file.exists()) {
            this.player = player.getName();
            this.uuid = player.getUniqueId().toString();
            this.kills = 0;
            this.deaths = 0;
            this.killStreak = 0;
            this.balance = 0.0;
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Stats stats = (Stats) ois.readObject();
            this.player = stats.player;
            this.uuid = stats.uuid;
            this.kills = stats.kills;
            this.deaths = stats.deaths;
            this.killStreak = stats.killStreak;
            this.balance = stats.balance;
            ois.close();
        } catch (Exception exception) {
            this.getPlayer().sendMessage(IPearlPvP.STARTER + ChatColor.RED + "Failed to load ur statistics file! Error: " + exception.getMessage());
        }
    }

    public Stats(OfflinePlayer player) {
        File file = new File("plugins/IPearlPvP/stats/" + player.getUniqueId() + ".stats");
        if (!file.exists()) {
            this.player = player.getName();
            this.uuid = player.getUniqueId().toString();
            this.kills = 0;
            this.deaths = 0;
            this.killStreak = 0;
            this.balance = 0.0;
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Stats stats = (Stats) ois.readObject();
            this.player = stats.player;
            this.uuid = stats.uuid;
            this.kills = stats.kills;
            this.deaths = stats.deaths;
            this.killStreak = stats.killStreak;
            this.balance = stats.balance;
            ois.close();
        } catch (Exception exception) {
            this.getPlayer().sendMessage(IPearlPvP.STARTER + ChatColor.RED + "Failed to load ur statistics file! Error: " + exception.getMessage());
        }
    }

    public void save() {
        try {
            File file = new File("plugins/IPearlPvP/stats/" + this.uuid + ".stats");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.close();
        } catch (Exception exception) {
            this.getPlayer().sendMessage(IPearlPvP.STARTER + ChatColor.RED + "Failed to save ur statistics file! Error: " + exception.getMessage());
        }
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
