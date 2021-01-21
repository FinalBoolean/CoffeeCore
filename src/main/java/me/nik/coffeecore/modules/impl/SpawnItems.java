package me.nik.coffeecore.modules.impl;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Permissions;
import me.nik.coffeecore.modules.Module;
import me.nik.coffeecore.utils.CoffeeUtils;
import me.nik.coffeecore.utils.Messenger;
import me.nik.coffeecore.utils.PlayerUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnItems extends Module {

    private final ItemStack pvpBotItem;
    private final ItemStack informationItem;
    private final ItemStack queueItem;
    private World spawn;

    public SpawnItems(CoffeeCore plugin) {
        super(plugin);

        this.pvpBotItem = CoffeeUtils.pvpBotItem();
        this.informationItem = CoffeeUtils.informationItem();
        this.queueItem = CoffeeUtils.queueItem();
    }

    @Override
    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        this.spawn = this.plugin.getServer().getWorld("world");
    }

    @EventHandler
    public void onWorldChange(final PlayerChangedWorldEvent e) {
        if (e.getFrom() == this.spawn) {
            removeItems(e.getPlayer());
        } else giveItems(e.getPlayer());
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        removeItems(e.getPlayer());
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        if (hasItems(e.getPlayer())) return;

        giveItems(e.getPlayer());
    }

    @EventHandler
    public void onDeath(final PlayerDeathEvent e) {
        if (e.getEntity().getWorld() != this.spawn) return;

        e.getDrops().clear();
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent e) {
        if (hasItems(e.getPlayer())) return;

        new BukkitRunnable() {
            public void run() {
                giveItems(e.getPlayer());
            }
        }.runTaskLater(this.plugin, 20);
    }

    @EventHandler
    public void onDrop(final PlayerDropItemEvent e) {
        final ItemStack item = e.getItemDrop().getItemStack();
        if (item != this.pvpBotItem || item != this.informationItem || item != this.queueItem) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onUse(final PlayerInteractEvent e) {
        if (!e.getAction().name().contains("RIGHT") || e.getPlayer().getWorld() != this.spawn) return;

        final ItemStack item = e.getItem();
        if (item == null) return;

        final Player p = e.getPlayer();

        if (PlayerUtils.getNmsPing(p) > 250 && !p.hasPermission(Permissions.ADMIN.getPermission())) {
            p.sendMessage(Messenger.PREFIX + "You can not fight yet, You're lagging like a maniac!");
            return;
        }

        if (item.equals(this.pvpBotItem)) {

            e.setCancelled(true);

            p.performCommand("pvpbot");

        } else if (item.equals(this.queueItem)) {

            e.setCancelled(true);

            p.performCommand("queue");

        }
    }

    public void giveItems(Player p) {
        Inventory inv = p.getInventory();

        inv.setItem(0, this.pvpBotItem);

        inv.setItem(4, this.informationItem);

        inv.setItem(8, this.queueItem);
    }

    public void removeItems(Player p) {
        Inventory inv = p.getInventory();

        inv.remove(this.pvpBotItem);

        inv.remove(this.informationItem);

        inv.remove(this.queueItem);
    }

    private boolean hasItems(Player p) {
        Inventory inv = p.getInventory();

        return inv.contains(this.pvpBotItem)
                && inv.contains(this.informationItem)
                && inv.contains(this.queueItem);
    }
}