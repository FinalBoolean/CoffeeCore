package me.nik.coffeecore.listeners;

import me.nik.coffeecore.CoffeeCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ProfileListener implements Listener {

    private final CoffeeCore plugin;

    public ProfileListener(CoffeeCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(final PlayerJoinEvent e) {
        this.plugin.getProfileManager().addProfile(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLeave(final PlayerQuitEvent e) {
        Player p = e.getPlayer();

        new BukkitRunnable() {
            public void run() {
                if (!p.isOnline()) plugin.getProfileManager().removeProfile(p);
            }
        }.runTaskLater(plugin, 20);
    }
}