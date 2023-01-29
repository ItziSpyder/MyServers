package me.improper.ipearlpvp.data;

import me.improper.ipearlpvp.IPearlPvP;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {

    private static FileConfiguration CONFIG = IPearlPvP.getInstance().getConfig();

    public class PLUGIN {
        public static String getPrefix() {
            return CONFIG.getString("config.plugin.prefix");
        }
    }

    public class MESSAGES {
        public static String getJoin() {
            List<String> list = CONFIG.getStringList("config.messages.join");
            return list.get(ran(list.size()));
        }
        public static String getNullDeath() {
            List<String> list = CONFIG.getStringList("config.messages.null_death");
            return list.get(ran(list.size()));
        }
        public static String getDeath() {
            List<String> list = CONFIG.getStringList("config.messages.death");
            return list.get(ran(list.size()));
        }
        public static String getLeave() {
            List<String> list = CONFIG.getStringList("config.messages.leave");
            return list.get(ran(list.size()));
        }
        public static String getDeathTitles() {
            List<String> list = CONFIG.getStringList("config.messages.death_title");
            return list.get(ran(list.size()));
        }
    }

    public class GAMEPLAY {
        public static int getMapReset() {
            return CONFIG.getInt("config.gameplay.map_reset_interval");
        }
        public static int getStarterMoney() {
            return CONFIG.getInt("config.gameplay.starter_money");
        }
        public static int getCombatTimer() {
            return CONFIG.getInt("config.gameplay.combat_timer");
        }
        public static List<String> getCombatBlacklist() {
            return  CONFIG.getStringList("config.gameplay.combat_command_blacklist");
        }
        public static String getMapResetCommand() {
            List<String> list =  CONFIG.getStringList("config.gameplay.reset_map_commands");
            return list.get(ran(list.size()));
        }
        public static List<String> getServerMapReset() {
            return CONFIG.getStringList("config.gameplay.server_on_map_reset");
        }
        public static List<String> getPlayersMapReset() {
            return CONFIG.getStringList("config.gameplay.players_on_map_reset");
        }
    }

    public class PLAYER {
        public static List<String> getJoinChatCommands() {
            return CONFIG.getStringList("config.player.on_join_send");
        }
    }



    private static int ran(int min, int max) {
        return min + (int) Math.floor(Math.random() * (max - min));
    }

    private static int ran(int max) {
        return (int) Math.floor(Math.random() * (max));
    }
}
