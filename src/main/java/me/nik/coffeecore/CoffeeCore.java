package me.nik.coffeecore;

import me.nik.coffeecore.commands.CommandManager;
import me.nik.coffeecore.listeners.ProfileListener;
import me.nik.coffeecore.managers.Config;
import me.nik.coffeecore.managers.ProfileManager;
import me.nik.coffeecore.modules.Module;
import me.nik.coffeecore.modules.impl.NoHunger;
import me.nik.coffeecore.modules.impl.ScaffoldAreas;
import me.nik.coffeecore.tasks.AlwaysDay;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class CoffeeCore extends JavaPlugin {

    private Config config;

    private ProfileManager profileManager;

    private final List<Module> modules = new ArrayList<>();

    @Override
    public void onEnable() {

        final Logger logger = this.getLogger();
        logger.info("Loading CoffeeCore, Made by Nik with <3");

        this.config = new Config();
        this.config.setup(this);
        this.config.addDefaults();
        this.config.get().options().copyDefaults(true);
        this.config.save();
        logger.info("Initialized Files");

        getCommand("coffee").setExecutor(new CommandManager(this));

        this.profileManager = new ProfileManager();

        initModules();
        logger.info("Initialized Modules");

        initListeners();
        logger.info("Initialized Listeners");

        initTasks();
        logger.info("Initialized Tasks");
    }

    private void initTasks() {
        new AlwaysDay().runTaskTimer(this, 1200, 1200);
    }

    private void initListeners() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new ProfileListener(this), this);
    }

    private void initModules() {

        this.modules.add(new ScaffoldAreas(this));
        this.modules.add(new NoHunger(this));

        this.modules.forEach(Module::init);
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    @Override
    public void onDisable() {

        this.modules.clear();

        this.config.reload();
        this.config.save();

        HandlerList.unregisterAll(this);
        this.getServer().getScheduler().cancelTasks(this);
    }
}