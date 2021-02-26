package me.nik.coffeecore.modules.impl;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.modules.Module;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class NoHunger extends Module {
    public NoHunger(CoffeeCore plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @Override
    public void disInit() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onHunger(final FoodLevelChangeEvent e) {
        e.setFoodLevel(20);
    }
}