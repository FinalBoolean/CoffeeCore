package me.nik.coffeecore.modules.impl;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Profile;
import me.nik.coffeecore.managers.VelocityMobManager;
import me.nik.coffeecore.modules.Module;
import me.nik.coffeecore.utils.CoffeeUtils;
import me.nik.coffeecore.utils.Messenger;
import me.nik.coffeecore.utils.custom.VelocityMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class VelocityMobs extends Module {
    private final VelocityMobManager velocityMobManager = new VelocityMobManager(this.plugin);
    private final List<VelocityMob> velocityMobs = new ArrayList<>();

    public VelocityMobs(CoffeeCore plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        this.velocityMobs.addAll(this.velocityMobManager.getVelocityMobs());

        this.velocityMobs.forEach(VelocityMob::spawn);

        //Just to make sure, i know i could have done it better using NMS but 1 im lazy and 2 im lazy
        new BukkitRunnable() {
            @Override
            public void run() {
                velocityMobs.forEach(VelocityMob::check);
            }
        }.runTaskTimer(this.plugin, 250, 250);
    }

    @Override
    public void disInit() {
        HandlerList.unregisterAll(this);

        this.velocityMobs.forEach(VelocityMob::kill);

        this.velocityMobs.clear();
    }

    @EventHandler
    public void onDamaged(final EntityDamageByEntityEvent e) {

        final int targetId = e.getEntity().getEntityId();

        boolean damagerVelocityMob = true;

        for (VelocityMob mob : this.velocityMobs) {

            if (mob.getEntity().getEntityId() == targetId) {
                damagerVelocityMob = false;
                break;
            }
        }

        if (damagerVelocityMob) {
            e.setDamage(0);
        } else e.setCancelled(true);
    }

    @EventHandler
    public void onMark(final PlayerInteractEvent e) {
        Profile profile = this.plugin.getProfile(e.getPlayer());
        if (!profile.isVelocityMobMode() || e.getItem() == null || !e.getItem().isSimilar(CoffeeUtils.velocityMobItem()))
            return;

        Player p = e.getPlayer();

        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            Location location = e.getClickedBlock().getLocation();
            profile.getVelocityMob().setLocation(location);
            p.sendMessage(Messenger.PREFIX + "Set location to X: " + location.getX() + " Y: " + location.getY() + " Z: " + location.getZ());
        }

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(final AsyncPlayerChatEvent e) {
        Profile profile = this.plugin.getProfile(e.getPlayer());
        if (!profile.isVelocityMobMode()) return;

        switch (e.getMessage().trim().toLowerCase()) {
            case "done":
                VelocityMob data = profile.getVelocityMob();
                this.velocityMobs.add(data);
                this.velocityMobManager.addData(data);
                profile.resetVelocityMob();
                profile.setVelocityMobMode(false);
                Bukkit.getScheduler().runTask(this.plugin, data::spawn);
                profile.getPlayer().getInventory().remove(CoffeeUtils.velocityMobItem());
                e.getPlayer().sendMessage(Messenger.PREFIX + "You have successfully created a velocity mob");
                break;
            case "cancel":
                profile.resetVelocityMob();
                profile.setVelocityMobMode(false);
                profile.getPlayer().getInventory().remove(CoffeeUtils.velocityMobItem());
                e.getPlayer().sendMessage(Messenger.PREFIX + "You have cancelled the velocity mob mode");
                break;
        }

        e.setCancelled(true);
    }
}