package me.nik.coffeecore.modules.impl;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Permissions;
import me.nik.coffeecore.modules.Module;
import me.nik.coffeecore.utils.Messenger;
import me.nik.coffeecore.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

public class PingFightListener extends Module {
    public PingFightListener(CoffeeCore plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        if (e.getPlayer().hasPermission(Permissions.ADMIN.getPermission())) return;

        final String[] args = e.getMessage().split("\\s");

        final boolean combatCommand = Arrays.stream(args).anyMatch(predicate -> predicate.contains("pvp") || predicate.contains("duel"));

        if (!combatCommand) return;

        final Player p = e.getPlayer();

        if (PlayerUtils.getNmsPing(p) < 500) return;

        e.setCancelled(true);

        p.sendMessage(Messenger.PREFIX + "You can not fight yet, You're lagging like a maniac!");
    }
}