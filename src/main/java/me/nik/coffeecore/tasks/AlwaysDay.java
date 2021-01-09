package me.nik.coffeecore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AlwaysDay extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getWorlds().forEach(world -> world.setTime(1000L));
    }
}