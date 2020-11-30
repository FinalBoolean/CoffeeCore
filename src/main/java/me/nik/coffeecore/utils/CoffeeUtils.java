package me.nik.coffeecore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class CoffeeUtils {

    private CoffeeUtils(){}

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
}