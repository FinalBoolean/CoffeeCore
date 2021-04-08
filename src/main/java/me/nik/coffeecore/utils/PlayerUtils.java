package me.nik.coffeecore.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;

public final class PlayerUtils {

    private PlayerUtils() {
    }

    public static int getNmsPing(final Player player) {
        if (player == null) return 0;

        try {
            final Object entityPlayer = ReflectionUtils.getMethod(player.getClass(), "getHandle").invoke(player);
            return (int) ReflectionUtils.getField(entityPlayer.getClass(), "ping").get(entityPlayer);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return 1;
        }
    }

    public static boolean getItem(final Player player, final ItemStack itemStack, boolean remove) {
        //We're using the item name in this case, Don't use this in production

        if (player.getInventory().getContents().length == 0) return false;

        final String itemName = itemStack.getItemMeta().getDisplayName();

        for (ItemStack item : player.getInventory().getContents()) {

            if (item == null) continue;

            ItemMeta meta = item.getItemMeta();

            if (meta == null || !meta.hasDisplayName()) continue;

            if (meta.getDisplayName().equals(itemName)) {
                if (remove) {
                    player.getInventory().removeItem(item);
                }
                return true;
            }
        }

        return false;
    }
}