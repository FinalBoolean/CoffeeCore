package me.nik.coffeecore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public final class Messenger {

    public static final String PREFIX = format("&8&l[&fCoffeeCore&8&l]&r ");

    private Messenger(){}

    public static String format(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void broadcast(String msg) {
        Bukkit.broadcastMessage(PREFIX + format(msg));
    }
}