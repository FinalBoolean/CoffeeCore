package me.nik.coffeecore.modules.impl;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.modules.Module;
import me.nik.coffeecore.utils.Messenger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AntiBot extends Module {
    private final Map<UUID, Long> cache = new ConcurrentHashMap<>();

    public AntiBot(CoffeeCore plugin) {
        super(plugin);
    }

    // This is enough, Cardinal's AntiBot is already very strong ^^

    @Override
    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(final PlayerJoinEvent e) {

        final long delay = 5000L + System.currentTimeMillis();

        this.cache.put(e.getPlayer().getUniqueId(), delay);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(final AsyncPlayerChatEvent e) {

        final UUID uuid = e.getPlayer().getUniqueId();

        if (!this.cache.containsKey(uuid)) return;

        final long time = this.cache.get(uuid);

        final long currentMillis = System.currentTimeMillis();

        if (time > currentMillis) {

            final long secondsLeft = (time / 1000L) - (currentMillis / 1000L);

            if (secondsLeft > 0) {

                e.setCancelled(true);

                e.getPlayer().sendMessage(Messenger.PREFIX + "You must wait " + secondsLeft + " after logging in.");

                return;
            }
        }

        this.cache.remove(uuid);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        this.cache.remove(e.getPlayer().getUniqueId());
    }
}