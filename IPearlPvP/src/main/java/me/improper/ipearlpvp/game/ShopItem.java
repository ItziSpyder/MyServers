package me.improper.ipearlpvp.game;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShopItem extends ItemStack {

    private ItemMeta meta;
    private int price;

    public ShopItem(ItemStack item) {
        super(item);
        this.meta = item.getItemMeta();
        this.price = 0;
    }

    public ShopItem(Material type) {
        super(new ItemStack(type));
        this.meta = super.getItemMeta();
        this.price = 0;
    }

    public ShopItem(ItemStack item, int price) {
        super(item);
        this.meta = item.getItemMeta();
        this.price = price;
    }

    public ShopItem(Material type, int price) {
        super(new ItemStack(type));
        this.meta = super.getItemMeta();
        this.price = price;
    }

    public ItemStack toShelfStack() {
        ShopItem item = (ShopItem) this.clone();
        List<String> lore = item.getLore();
        lore.add("");
        lore.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + price);
        item.setLore(lore);
        return item;
    }

    public ItemStack toProduct() {
        List<String> lore = this.getLore();
        lore.removeIf(s -> s.contains(ChatColor.GRAY + "Price: " + ChatColor.GREEN));
        this.setLore(lore);
        return this;
    }

    public String getDisplayName() {
        if (meta.getDisplayName().trim().equals("")) return this.getType().name();
        return meta.getDisplayName();
    }

    public int getPrice() {
        return this.price;
    }

    public List<String> getLore() {
        if (meta.getLore() == null) return new ArrayList<>();
        return meta.getLore();
    }

    public Map<Enchantment,Integer> getEnchants() {
        return meta.getEnchants();
    }

    public Set<ItemFlag> getItemFlags() {
        return meta.getItemFlags();
    }

    public boolean isUnbreakable() {
        return meta.isUnbreakable();
    }

    public int getCustomModelData() {
        return meta.getCustomModelData();
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDisplayName(String displayName) {
        meta.setDisplayName(displayName);
        super.setItemMeta(meta);
    }

    public void setLore(List<String> lore) {
        meta.setLore(lore);
        super.setItemMeta(meta);
    }

    public void addItemFlags(ItemFlag... itemFlags) {
        meta.addItemFlags(itemFlags);
        super.setItemMeta(meta);
    }

    public void setEnchants(Enchantment enchant, int level, boolean ignoreRestrictions) {
        meta.addEnchant(enchant,level,ignoreRestrictions);
        super.setItemMeta(meta);
    }

    public void removeEnchant(Enchantment enchantment) {
        meta.removeEnchant(enchantment);
        super.setItemMeta(meta);
    }

    public void removeItemFlag(ItemFlag... itemFlags) {
        meta.removeItemFlags(itemFlags);
        super.setItemMeta(meta);
    }

    public void removeAllItemFlags() {
        for (ItemFlag flag : ItemFlag.values()) meta.removeItemFlags(flag);
        super.setItemMeta(meta);
    }

    public void addAllItemFlags() {
        for (ItemFlag flag : ItemFlag.values()) meta.addItemFlags(flag);
        super.setItemMeta(meta);
    }

    public void setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        super.setItemMeta(meta);
    }

    public void setCustomModelData(int customModelData) {
        meta.setCustomModelData(customModelData);
        super.setItemMeta(meta);
    }
}
