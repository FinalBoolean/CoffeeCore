package me.nik.coffeecore.modules.impl;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Profile;
import me.nik.coffeecore.managers.ScaffoldAreaManager;
import me.nik.coffeecore.modules.Module;
import me.nik.coffeecore.utils.CoffeeUtils;
import me.nik.coffeecore.utils.Messenger;
import me.nik.coffeecore.utils.custom.ScaffoldAreaData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ScaffoldArea extends Module {
    public ScaffoldArea(CoffeeCore plugin) {
        super(plugin);
    }

    private final List<ScaffoldAreaData> scaffoldAreas = new ArrayList<>();
    private final ScaffoldAreaManager manager = new ScaffoldAreaManager();

    @Override
    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        scaffoldAreas.addAll(this.manager.getData());

        new BukkitRunnable() {
            public void run() {
                if (scaffoldAreas.isEmpty()) return;

                scaffoldAreas.forEach(ScaffoldAreaData::clean);

                Messenger.broadcast("Cleaned up the Scaffold Area(s)");
            }
        }.runTaskTimer(this.plugin, 20 * 30, 20 * 30);
    }

    @EventHandler
    public void onMark(final PlayerInteractEvent e) {
        Profile profile = this.plugin.getProfileManager().getProfile(e.getPlayer());
        if (!profile.isScaffoldMode() || e.getItem() == null || !e.getItem().isSimilar(CoffeeUtils.scaffoldArenaItem())) return;

        Player p = e.getPlayer();

        switch (e.getAction()) {
            case LEFT_CLICK_BLOCK:
                Location one = e.getClickedBlock().getLocation();
                profile.getScaffoldAreaData().setOne(one);
                p.sendMessage(Messenger.PREFIX + "Set the first location to X: " + one.getX() + " Y: " + one.getY() + " Z: " + one.getZ());
                break;
            case RIGHT_CLICK_BLOCK:
                Location two = e.getClickedBlock().getLocation();
                profile.getScaffoldAreaData().setTwo(two);
                p.sendMessage(Messenger.PREFIX + "Set the second location to X: " + two.getX() + " Y: " + two.getY() + " Z: " + two.getZ());
                break;
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final AsyncPlayerChatEvent e) {
        Profile profile = this.plugin.getProfileManager().getProfile(e.getPlayer());
        if (!profile.isScaffoldMode()) return;

        switch (e.getMessage().trim().toLowerCase()) {
            case "done":
                ScaffoldAreaData data = profile.getScaffoldAreaData();
                this.scaffoldAreas.add(data);
                this.manager.addData(data);
                profile.resetScaffoldAreaData();
                profile.setScaffoldMode(false);
                e.getPlayer().sendMessage(Messenger.PREFIX + "You have successfully created a scaffold arena");
                break;
            case "cancel":
                profile.resetScaffoldAreaData();
                profile.setScaffoldMode(false);
                e.getPlayer().sendMessage(Messenger.PREFIX + "You have cancelled the scaffold arena mode");
                break;
        }

        e.setCancelled(true);
    }
}