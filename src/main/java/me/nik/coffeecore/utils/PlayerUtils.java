package me.nik.coffeecore.utils;

import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public final class PlayerUtils {

    private PlayerUtils() {
    }

    public static int getNmsPing(final Player player) {
        try {
            final Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            return (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException | NoSuchMethodException e) {
            return 1;
        }
    }
}