package me.nik.coffeecore.modules.impl;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Profile;
import me.nik.coffeecore.managers.ScaffoldAreaManager;
import me.nik.coffeecore.modules.Module;
import me.nik.coffeecore.utils.CoffeeUtils;
import me.nik.coffeecore.utils.Messenger;
import me.nik.coffeecore.utils.custom.ScaffoldArea;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ScaffoldAreas extends Module {
    private final List<ScaffoldArea> scaffoldAreas = new ArrayList<>();
    private final ScaffoldAreaManager manager = new ScaffoldAreaManager(this.plugin);

    public ScaffoldAreas(CoffeeCore plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        this.scaffoldAreas.addAll(this.manager.getScaffoldAreas());

        new BukkitRunnable() {
            public void run() {
                if (scaffoldAreas.isEmpty()) return;

                scaffoldAreas.forEach(ScaffoldArea::clean);

                Messenger.broadcast("Cleaned up the Scaffold Area(s)");
            }
        }.runTaskTimer(this.plugin, 20 * 30, 20 * 30);
    }

    @EventHandler
    public void onMark(final PlayerInteractEvent e) {
        Profile profile = this.plugin.getProfile(e.getPlayer());
        if (!profile.isScaffoldMode() || e.getItem() == null || !e.getItem().isSimilar(CoffeeUtils.scaffoldArenaItem())) return;

        Player p = e.getPlayer();

        switch (e.getAction()) {
            case LEFT_CLICK_BLOCK:
                Location one = e.getClickedBlock().getLocation();
                profile.getScaffoldArea().setOne(one);
                p.sendMessage(Messenger.PREFIX + "Set the first location to X: " + one.getX() + " Y: " + one.getY() + " Z: " + one.getZ());
                break;
            case RIGHT_CLICK_BLOCK:
                Location two = e.getClickedBlock().getLocation();
                profile.getScaffoldArea().setTwo(two);
                p.sendMessage(Messenger.PREFIX + "Set the second location to X: " + two.getX() + " Y: " + two.getY() + " Z: " + two.getZ());
                break;
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(final AsyncPlayerChatEvent e) {
        Profile profile = this.plugin.getProfile(e.getPlayer());
        if (!profile.isScaffoldMode()) return;

        switch (e.getMessage().trim().toLowerCase()) {
            case "done":
                ScaffoldArea data = profile.getScaffoldArea();
                this.scaffoldAreas.add(data);
                this.manager.addData(data);
                profile.resetScaffoldArea();
                profile.setScaffoldMode(false);
                e.getPlayer().sendMessage(Messenger.PREFIX + "You have successfully created a scaffold arena");
                break;
            case "cancel":
                profile.resetScaffoldArea();
                profile.setScaffoldMode(false);
                e.getPlayer().sendMessage(Messenger.PREFIX + "You have cancelled the scaffold arena mode");
                break;
        }

        e.setCancelled(true);
    }
}