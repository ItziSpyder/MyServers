package me.improper.ipearlpvp.game.inventory;

import me.improper.ipearlpvp.IPearlPvP;
import me.improper.ipearlpvp.game.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shop {

    public static void registerAll() {
        setSHOPMENU();
        setARMOR();
        setMISC();
        setWEAPONS();
    }

    public static Inventory SHOPMENU;
    public static Inventory ARMOR;
    public static Inventory MISC;
    public static Inventory WEAPONS;

    static void setSHOPMENU() {
        Inventory menu = Bukkit.createInventory(null,54, IPearlPvP.STARTER + ChatColor.YELLOW + "Shop");
        // ====={ Menu Items }=====
        ShopItem x = new ShopItem(Material.BLACK_STAINED_GLASS_PANE);
        x.setDisplayName(" ");
        ShopItem y = new ShopItem(Material.CYAN_STAINED_GLASS_PANE);
        y.setDisplayName(" ");
        ShopItem ARMOR = new ShopItem(Material.NETHERITE_CHESTPLATE);
        ARMOR.setDisplayName(ChatColor.AQUA + "Armor");
        ARMOR.addAllItemFlags();
        ShopItem WEAPONS = new ShopItem(Material.DIAMOND_SWORD);
        WEAPONS.setDisplayName(ChatColor.AQUA + "Weapons");
        WEAPONS.addAllItemFlags();
        ShopItem MISC = new ShopItem(Material.GOLDEN_APPLE);
        MISC.setDisplayName(ChatColor.AQUA + "Misc");
        // ====={ Menu Registry }=====
        menu.setContents(new ItemStack[]{
                y,y,y,y,y,y,y,y,y,
                y,x,x,x,x,x,x,x,y,
                y,x,WEAPONS,x,ARMOR,x,MISC,x,y,
                y,x,x,x,x,x,x,x,y,
                y,x,x,x,x,x,x,x,y,
                y,y,y,y,y,y,y,y,y,
        });
        SHOPMENU = menu;
    }

    public static void setARMOR() {
        Inventory menu = Bukkit.createInventory(null,54, IPearlPvP.STARTER + ChatColor.YELLOW + "Shop/Armor");
        ShopItem x = new ShopItem(Material.BLACK_STAINED_GLASS_PANE);
        x.setDisplayName(" ");
        ShopItem y = new ShopItem(Material.CYAN_STAINED_GLASS_PANE);
        y.setDisplayName(" ");
        ShopItem a = new ShopItem(Material.AIR);
        ShopItem back = new ShopItem(Material.ARROW);
        back.setDisplayName(ChatColor.WHITE + "<---- Back");
        back.setLore(List.of(ChatColor.GRAY + "to previous page"));
        menu.setContents(new ItemStack[]{
                y,y,y,y,y,y,y,y,y,
                y,a,a,a,a,a,a,a,y,
                y,a,a,a,a,a,a,a,y,
                y,a,a,a,a,a,a,a,y,
                y,a,a,a,a,a,a,a,y,
                y,y,y,y,back,y,y,y,y,
        });
        // ====={ Menu Items }=====
        List<Material> list = new ArrayList<>(Arrays.asList(Material.values()));
        list.removeIf(m -> !isArmor(m));
        for (Material type : list) {
            ShopItem item = new ShopItem(type);
            String name = type.name();
            item.setEnchants(Enchantment.PROTECTION_ENVIRONMENTAL,4,true);
            item.setEnchants(Enchantment.DURABILITY,3,true);
            item.setEnchants(Enchantment.MENDING,1,true);
            if (name.contains("LEATHER")) item.setPrice(5);
            else if (name.contains("CHAINMAIL")) item.setPrice(10);
            else if (name.contains("GOLDEN")) item.setPrice(1);
            else if (name.contains("IRON")) item.setPrice(50);
            else if (name.contains("DIAMOND")) item.setPrice(100);
            else if (name.contains("NETHERITE")) item.setPrice(200);
            if (menu.firstEmpty() != -1) menu.setItem(menu.firstEmpty(),item.toShelfStack());
        }
        // ====={ Menu Registry }=====
        while (menu.firstEmpty() != -1) menu.setItem(menu.firstEmpty(),x);
        ARMOR = menu;
    }

    public static void setWEAPONS() {
        Inventory menu = Bukkit.createInventory(null,54, IPearlPvP.STARTER + ChatColor.YELLOW + "Shop/Weapons");
        ShopItem x = new ShopItem(Material.BLACK_STAINED_GLASS_PANE);
        x.setDisplayName(" ");
        ShopItem y = new ShopItem(Material.CYAN_STAINED_GLASS_PANE);
        y.setDisplayName(" ");
        ShopItem a = new ShopItem(Material.AIR);
        ShopItem back = new ShopItem(Material.ARROW);
        back.setDisplayName(ChatColor.WHITE + "<---- Back");
        back.setLore(List.of(ChatColor.GRAY + "to previous page"));
        menu.setContents(new ItemStack[]{
                y,y,y,y,y,y,y,y,y,
                y,a,a,a,a,a,a,a,y,
                y,a,a,a,a,a,a,a,y,
                y,a,a,a,a,a,a,a,y,
                y,a,a,a,a,a,a,a,y,
                y,y,y,y,back,y,y,y,y,
        });
        // ====={ Menu Items }=====
        List<Material> list = new ArrayList<>(Arrays.asList(Material.values()));
        list.removeIf(m -> !isWeapon(m));
        for (Material type : list) {
            ShopItem item = new ShopItem(type);
            String name = type.name();
            if (name.contains("SWORD")) {
                item.setEnchants(Enchantment.DAMAGE_ALL,5,false);
                item.setEnchants(Enchantment.KNOCKBACK,1,false);
            }
            else if (name.contains("AXE")) {
                item.setEnchants(Enchantment.DAMAGE_ALL,5,false);
                item.setEnchants(Enchantment.DIG_SPEED,5,false);
            }
            else if (name.contains("CROSSBOW")) {
                item.setEnchants(Enchantment.PIERCING,4,false);
                item.setEnchants(Enchantment.QUICK_CHARGE,3,false);
            }
            else if (name.contains("BOW")) {
                item.setEnchants(Enchantment.ARROW_DAMAGE,5,false);
                item.setEnchants(Enchantment.ARROW_KNOCKBACK,2,false);
                item.setEnchants(Enchantment.ARROW_FIRE,1,false);
            }
            else if (name.contains("TRIDENT")) {
                item.setEnchants(Enchantment.RIPTIDE,3,false);
                item.setEnchants(Enchantment.IMPALING,5,false);
                item.setEnchants(Enchantment.DAMAGE_ALL,5,false);
            }
            if (name.contains("WOODEN")) item.setPrice(5);
            else if (name.contains("STONE")) item.setPrice(10);
            else if (name.contains("GOLDEN")) item.setPrice(1);
            else if (name.contains("IRON")) item.setPrice(50);
            else if (name.contains("DIAMOND")) item.setPrice(100);
            else if (name.contains("NETHERITE") || name.contains("TRIDENT")) item.setPrice(200);
            else if (name.contains("BOW") || name.contains("SHIELD")) item.setPrice(75);
            item.setEnchants(Enchantment.DURABILITY,3,true);
            item.setEnchants(Enchantment.MENDING,1,true);
            if (menu.firstEmpty() != -1)  menu.setItem(menu.firstEmpty(),item.toShelfStack());
        }
        // ====={ Menu Registry }=====
        while (menu.firstEmpty() != -1) menu.setItem(menu.firstEmpty(),x);
        WEAPONS = menu;
    }

    public static void setMISC() {
        Inventory menu = Bukkit.createInventory(null,54, IPearlPvP.STARTER + ChatColor.YELLOW + "Shop/Misc");
        ShopItem x = new ShopItem(Material.BLACK_STAINED_GLASS_PANE);
        x.setDisplayName(" ");
        ShopItem y = new ShopItem(Material.CYAN_STAINED_GLASS_PANE);
        y.setDisplayName(" ");
        ShopItem a = new ShopItem(Material.AIR);
        ShopItem back = new ShopItem(Material.ARROW);

        ItemStack slowfall = new ItemStack(Material.TIPPED_ARROW,64);
        PotionMeta slowfallM = (PotionMeta) slowfall.getItemMeta();
        slowfallM.setBasePotionData(new PotionData(PotionType.SLOW_FALLING));
        slowfall.setItemMeta(slowfallM);
        slowfall = new ShopItem(slowfall,200).toShelfStack();

        ItemStack weakness = new ItemStack(Material.TIPPED_ARROW,64);
        PotionMeta weaknessM = (PotionMeta) weakness.getItemMeta();
        weaknessM.setBasePotionData(new PotionData(PotionType.WEAKNESS));
        weakness.setItemMeta(weaknessM);
        weakness = new ShopItem(weakness,200).toShelfStack();


        back.setDisplayName(ChatColor.WHITE + "<---- Back");
        back.setLore(List.of(ChatColor.GRAY + "to previous page"));
        menu.setContents(new ItemStack[]{
                y,y,y,y,y,y,y,y,y,
                y,slowfall,weakness,a,a,a,a,a,y,
                y,a,a,a,a,a,a,a,y,
                y,a,a,a,a,a,a,a,y,
                y,a,a,a,a,a,a,a,y,
                y,y,y,y,back,y,y,y,y,
        });
        // ====={ Menu Items }=====
        List<Material> list = new ArrayList<>(Arrays.asList(Material.values()));
        list.removeIf(m -> !isMisc(m));
        for (Material type : list) {
            ShopItem item = new ShopItem(type);
            String name = type.name();

            if (name.contains("RESPAWN") || name.contains("CRYSTAL")) {
                item.setPrice(50);
                item.setAmount(64);
            }
            else if (name.contains("COBWEB") || name.contains("GLOWSTONE") || name.contains("OBSIDIAN") || name.contains("LOG") || name.contains("COBBLESTONE") || name.contains("EXPERIENCE_BOTTLE")) {
                item.setPrice(10);
                item.setAmount(64);
            }
            else if (name.contains("ENCHANTED_GOLDEN_APPLE")) {
                item.setPrice(200);
                item.setAmount(64);
            }
            else if (name.contains("GOLDEN_APPLE")) {
                item.setPrice(80);
                item.setAmount(64);
            }
            else if (name.contains("GOLDEN_CARROT") || name.contains("TNT")) {
                item.setPrice(30);
                item.setAmount(64);
            }
            else if (name.contains("BUCKET")) {
                item.setPrice(25);
                item.setAmount(1);
            }
            else if (name.contains("PEARL")) {
                item.setPrice(30);
                item.setAmount(16);
            }
            else if (name.contains("SPECTRAL_ARROW")) {
                item.setPrice(50);
                item.setAmount(64);
            }
            else if (name.contains("ARROW")) {
                item.setPrice(15);
                item.setAmount(64);
            }
            else if (name.contains("TOTEM")) {
                item.setPrice(15);
                item.setAmount(1);
            }

            if (menu.firstEmpty() != -1)  menu.setItem(menu.firstEmpty(),item.toShelfStack());
        }
        // ====={ Menu Registry }=====
        while (menu.firstEmpty() != -1) menu.setItem(menu.firstEmpty(),x);
        MISC = menu;
    }



    static boolean isArmor(Material type) {
        String name = type.name();
        return name.contains("LEGGINGS") ||
                name.contains("HELMET") ||
                name.contains("CHESTPLATE") ||
                name.contains("BOOTS");
    }

    static boolean isWeapon(Material type) {
        String name = type.name();
        return name.contains("SWORD") ||
                name.contains("_AXE") ||
                name.contains("PICKAXE") ||
                name.contains("SHIELD") ||
                name.contains("TRIDENT") ||
                (name.contains("BOW") && !name.contains("BOWL")) ||
                name.contains("CROSSBOW");
    }

    static boolean isMisc(Material type) {
        String name = type.name();
        return (name.contains("ARROW") && !name.contains("TIPPED")) ||
                name.contains("RESPAWN") ||
                (name.contains("GLOWSTONE") && !name.contains("DUST")) ||
                name.contains("END_CRYSTAL") ||
                (name.contains("OBSIDIAN") && !name.contains("CRYING")) ||
                name.contains("GOLDEN_APPLE") ||
                name.contains("GOLDEN_CARROT") ||
                name.contains("PEARL") ||
                name.contains("WATER") ||
                name.contains("LAVA") ||
                (name.contains("TNT") && !name.contains("_")) ||
                (name.contains("COBBLESTONE") && !name.contains("_")) ||
                (name.contains("OAK_LOG") && !name.contains("_OAK")) ||
                name.contains("EXPERIENCE_BOTTLE") ||
                name.contains("TOTEM") ||
                name.contains("COBWEB");
    }
}
