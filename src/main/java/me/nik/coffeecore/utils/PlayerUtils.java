package me.nik.coffeecore.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class PlayerUtils {

    private PlayerUtils() {
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