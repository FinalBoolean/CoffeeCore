package me.nik.coffeecore.modules.impl;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Permissions;
import me.nik.coffeecore.modules.Module;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class WorldDownloader extends Module implements PluginMessageListener {
    private static final String WDL_INIT = "WDL|INIT";
    private static final String WDL_CONTROL = "WDL|CONTROL";
    private static final String IPBAN = "cardinal ipban %player% 999d You need to learn to appreciate coffee my man.";

    public WorldDownloader(CoffeeCore plugin) {
        super(plugin);
    }

    @Override
    public void init() {

        this.plugin.getServer().getMessenger().registerIncomingPluginChannel(this.plugin, WDL_INIT, this);
        this.plugin.getServer().getMessenger().registerOutgoingPluginChannel(this.plugin, WDL_CONTROL);
    }

    @Override
    public void disInit() {
        this.plugin.getServer().getMessenger().unregisterIncomingPluginChannel(this.plugin, WDL_INIT);
        this.plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(this.plugin, WDL_CONTROL);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (channel.equals(WDL_INIT) || channel.equals(WDL_CONTROL)) {

            if (player.hasPermission(Permissions.ADMIN.getPermission())) return;

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), IPBAN.replace("%player%", player.getName()));
        }
    }
}