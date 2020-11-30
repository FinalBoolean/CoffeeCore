package me.nik.coffeecore.modules;

import me.nik.coffeecore.CoffeeCore;
import org.bukkit.event.Listener;

public abstract class Module implements Listener {

    protected final CoffeeCore plugin;

    public Module(CoffeeCore plugin) {
        this.plugin = plugin;
    }

    public abstract void init();
}