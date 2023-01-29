package me.improper.ipearlpvp.game.players;

import org.bukkit.ChatColor;

public class Rank {

    private String name;
    private String prefix;
    private String suffix;
    private ChatColor color;
    private int level;

    public Rank(String name) {
        this.name = name;
        this.prefix = name;
        this.suffix = "";
        this.color = ChatColor.WHITE;
        this.level = 0;
    }

    public Rank(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = "";
        this.color = ChatColor.WHITE;
        this.level = 0;
    }

    public Rank(String name, String prefix, String suffix, ChatColor color, int level) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.color = color;
        this.level = level;
    }

    public boolean isEqualTo(Rank rank) {
        if (this.level == rank.getLevel()) return true;
        else return false;
    }

    public Rank getHigher(Rank rank) {
        if (this.level >= rank.getLevel()) return this;
        else return rank;
    }

    public Rank getLower(Rank rank) {
        if (this.level <= rank.getLevel()) return this;
        else return rank;
    }

    public Rank(String name, int level) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }
}
