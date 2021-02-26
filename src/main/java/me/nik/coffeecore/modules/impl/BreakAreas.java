package me.nik.coffeecore.modules.impl;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Profile;
import me.nik.coffeecore.managers.BreakAreaManager;
import me.nik.coffeecore.modules.Module;
import me.nik.coffeecore.utils.CoffeeUtils;
import me.nik.coffeecore.utils.Messenger;
import me.nik.coffeecore.utils.PlayerUtils;
import me.nik.coffeecore.utils.custom.BreakArea;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BreakAreas extends Module {

    private final List<BreakArea> breakAreas = new ArrayList<>();

    private final BreakAreaManager manager = new BreakAreaManager(this.plugin);

    private final ItemStack coffeePickaxe = CoffeeUtils.coffeePickaxe();

    public BreakAreas(CoffeeCore plugin) {
        super(plugin);
    }

    @Override
    public void disInit() {
        HandlerList.unregisterAll(this);

        this.breakAreas.clear();
    }

    @Override
    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        this.breakAreas.addAll(this.manager.getBreakAreas());

        new BukkitRunnable() {
            public void run() {
                if (breakAreas.isEmpty()) return;

                breakAreas.forEach(BreakArea::rebuild);

                Messenger.broadcast("Rebuilt the Break Area(s)");
            }
        }.runTaskTimer(this.plugin, 20 * 120, 20 * 120);
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent e) {

        Player p = e.getPlayer();

        boolean inside = false;

        for (BreakArea area : this.breakAreas) {
            if (area.isNearArea(p)) {
                inside = true;
                break;
            }
        }

        final boolean hasPickaxe = PlayerUtils.getItem(p, this.coffeePickaxe, false);

        if (inside) {
            if (!hasPickaxe) {
                p.getInventory().addItem(this.coffeePickaxe);
            }
        } else if (hasPickaxe) {
            PlayerUtils.getItem(p, this.coffeePickaxe, true);
            p.getInventory().remove(Material.COBBLESTONE);
        }
    }

    @EventHandler
    public void onMark(final PlayerInteractEvent e) {
        Profile profile = this.plugin.getProfile(e.getPlayer());
        if (!profile.isBreakMode() || e.getItem() == null || !e.getItem().isSimilar(CoffeeUtils.breakAreaItem()))
            return;

        Player p = e.getPlayer();

        switch (e.getAction()) {
            case LEFT_CLICK_BLOCK:
                Location one = e.getClickedBlock().getLocation();
                profile.getBreakArea().setOne(one);
                p.sendMessage(Messenger.PREFIX + "Set the first location to X: " + one.getX() + " Y: " + one.getY() + " Z: " + one.getZ());
                break;
            case RIGHT_CLICK_BLOCK:
                Location two = e.getClickedBlock().getLocation();
                profile.getBreakArea().setTwo(two);
                p.sendMessage(Messenger.PREFIX + "Set the second location to X: " + two.getX() + " Y: " + two.getY() + " Z: " + two.getZ());
                break;
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(final AsyncPlayerChatEvent e) {
        Profile profile = this.plugin.getProfile(e.getPlayer());
        if (!profile.isBreakMode()) return;

        switch (e.getMessage().trim().toLowerCase()) {
            case "done":
                BreakArea data = profile.getBreakArea();
                this.breakAreas.add(data);
                this.manager.addData(data);
                profile.resetBreakArea();
                profile.setBreakMode(false);
                profile.getPlayer().getInventory().remove(CoffeeUtils.breakAreaItem());
                e.getPlayer().sendMessage(Messenger.PREFIX + "You have successfully created a break area");
                break;
            case "cancel":
                profile.resetBreakArea();
                profile.setBreakMode(false);
                profile.getPlayer().getInventory().remove(CoffeeUtils.breakAreaItem());
                e.getPlayer().sendMessage(Messenger.PREFIX + "You have cancelled the break area mode");
                break;
        }

        e.setCancelled(true);
    }
}