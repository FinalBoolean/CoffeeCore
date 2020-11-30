package me.nik.coffeecore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class AlwaysDay extends BukkitRunnable {
    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            world.setTime(1000);
        }
    }
}