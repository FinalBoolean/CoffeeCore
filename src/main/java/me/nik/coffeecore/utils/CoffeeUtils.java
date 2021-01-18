package me.nik.coffeecore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class CoffeeUtils {

    private CoffeeUtils() {
    }

    /**
     * Yes this class is full of unnecessary static, Do not do this.
     */

    public static ItemStack scaffoldArenaItem() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Messenger.PREFIX + "Arena Tool");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Messenger.format("&7Right Click &8~ &7Left Click"));
        lore.add("");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack commandSignItem() {
        ItemStack item = new ItemStack(Material.valueOf("SIGN"));
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Messenger.PREFIX + "Command Sign");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Messenger.format("&7Place it anywhere to set it"));
        lore.add("");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack pvpBotItem() {
        ItemStack item = new ItemStack(Material.valueOf("DIAMOND_SWORD"));
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Messenger.PREFIX + "PvP Bot");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Messenger.format("&7Right Click to PvP With the Bot"));
        lore.add("");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack informationItem() {
        ItemStack item = new ItemStack(Material.valueOf("BOOK"));
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Messenger.PREFIX + "Information");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Messenger.format("&7Use /Duel to duel with players"));
        lore.add("");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack queueItem() {
        ItemStack item = new ItemStack(Material.valueOf("IRON_SWORD"));
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Messenger.PREFIX + "Join the Queue");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Messenger.format("&7Right Click to join the Queue"));
        lore.add("");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }
}