package me.improper.ipearlpvp.server;

import me.improper.ipearlpvp.IPearlPvP;
import me.improper.ipearlpvp.data.Config;
import me.improper.ipearlpvp.game.players.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class ServerUtils {

    public static List<String> listPlayers() {
        List<String> list = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()) list.add(p.getName());
        return list;
    }

    public static List<String> listStaffs() {
        List<String> list = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()) if (p.isOp()) list.add(p.getName());
        return list;
    }

    public static Player mostKs() {
        Player mostKs = Bukkit.getOnlinePlayers().toArray(new Player[0])[0];
        double highestKs = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            Stats stats = new Stats(p);
            if (stats.getKillStreak() > highestKs) {
                highestKs = stats.getKdr();
                mostKs = p;
            }
        }
        return mostKs;
    }

    public class SCOREBOARD {
        public static void checkLoop() {
            for (Player p : Bukkit.getOnlinePlayers()) {
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard board = manager.getNewScoreboard();
                Objective objective = board.registerNewObjective("sidebar","dummy", IPearlPvP.STARTER);
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                Stats stats = new Stats(p);
                Score score1 = objective.getScore("");
                Score score2 = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + p.getName());
                Score score3 = objective.getScore(ChatColor.YELLOW + "   - Kills: " + ChatColor.WHITE + stats.getKills());
                Score score4 = objective.getScore(ChatColor.YELLOW + "   - Deaths: " + ChatColor.WHITE + stats.getDeaths());
                Score score5 = objective.getScore(ChatColor.YELLOW + "   - KDR: " + ChatColor.WHITE + stats.getKdr());
                Score score6 = objective.getScore(ChatColor.YELLOW + "   - Balance: " + ChatColor.GREEN + stats.getStringBal());
                Score score7 = objective.getScore(ChatColor.YELLOW + " ");
                Score score8 = objective.getScore(ChatColor.GOLD + "" + ChatColor.BOLD + "Server");
                Score score9 = objective.getScore(ChatColor.YELLOW + "   - Map Reset: " + ChatColor.WHITE + ServerUtils.MAPRESET.getTimeLeft());
                Score score10 = objective.getScore(ChatColor.YELLOW + "   - Top: " + ChatColor.WHITE + mostKs().getName());
                Score score11 = objective.getScore(ChatColor.YELLOW + "   - Online: " + ChatColor.WHITE + Bukkit.getOnlinePlayers().size());
                Score score12 = objective.getScore(ChatColor.YELLOW + "  ");

                score1.setScore(10);
                score2.setScore(9);
                score3.setScore(8);
                score4.setScore(7);
                score5.setScore(6);
                score6.setScore(5);
                score7.setScore(4);
                score8.setScore(3);
                score9.setScore(2);
                score10.setScore(1);
                score11.setScore(0);
                score12.setScore(-1);

                p.setScoreboard(board);
            }
        }
    }

    public class MAPRESET {
        private static long NEXTRESET = System.currentTimeMillis() + (Config.GAMEPLAY.getMapReset() * 1000L);
        public static boolean RESUME = true;
        public static void resetTimer() {
            NEXTRESET = System.currentTimeMillis() + (Config.GAMEPLAY.getMapReset() * 1000L);
        }
        public static double getTimeLeft() {
            return Math.floor((NEXTRESET - System.currentTimeMillis()) / 10.0) / 100.0;
        }
        public static void resetMap() {
            NEXTRESET = System.currentTimeMillis() + (11 * 1000);
        }
        public static void checkTimer() {
            if (!RESUME) return;
            if (getTimeLeft() <= 0) {
                ServerSound end = new ServerSound(null, Sound.ENTITY_WITHER_AMBIENT,10,0.1F);
                end.playAllAt();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendTitle(ChatColor.GOLD + mostKs().getName(), ChatColor.YELLOW + "has the highest kill streak!",10,100,10);
                    for (String cmd : Config.GAMEPLAY.getPlayersMapReset()) {
                        p.chat(cmd);
                    }
                }
                resetTimer();
            }
            else if (getTimeLeft() > 0 && getTimeLeft() <= 10) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendTitle(ChatColor.GOLD + "Map resetting...",ChatColor.YELLOW + "" + getTimeLeft(),0,20,0);
                }
            }
            else if (getTimeLeft() == 10) {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),Config.GAMEPLAY.getMapResetCommand());
                for (String cmd : Config.GAMEPLAY.getServerMapReset()) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),cmd);
                }
            }
        }
    }
}
