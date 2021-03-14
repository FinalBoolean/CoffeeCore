package me.nik.coffeecore;

import me.nik.coffeecore.commands.CommandManager;
import me.nik.coffeecore.listener.AliceVerboseListener;
import me.nik.coffeecore.managers.Config;
import me.nik.coffeecore.modules.Module;
import me.nik.coffeecore.modules.impl.AntiBot;
import me.nik.coffeecore.modules.impl.BreakAreas;
import me.nik.coffeecore.modules.impl.CommandSigns;
import me.nik.coffeecore.modules.impl.NoHunger;
import me.nik.coffeecore.modules.impl.ScaffoldAreas;
import me.nik.coffeecore.modules.impl.SpawnItems;
import me.nik.coffeecore.modules.impl.VelocityMobs;
import me.nik.coffeecore.modules.impl.WorldDownloader;
import me.nik.coffeecore.modules.impl.anticrash.AntiCrash;
import me.nik.coffeecore.tasks.AlwaysDay;
import me.nik.coffeecore.utils.MillisTest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public final class CoffeeCore extends JavaPlugin {

    private Config config;

    private final Map<UUID, Profile> profiles = new HashMap<>();

    private final List<Module> modules = new ArrayList<>();

    private static CoffeeCore instance;

    public static CoffeeCore getInstance() {
        return instance;
    }

    private void initTasks() {
        new AlwaysDay().runTaskTimer(this, 1200, 1200);
    }

    private void initListeners() {

        final PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new AliceVerboseListener(), this);

    }

    @Override
    public void onEnable() {
        instance = this;

        MillisTest test = new MillisTest();

        final Logger logger = this.getLogger();
        logger.info("Loading CoffeeCore, Made by Nik with <3");

        this.config = new Config();
        this.config.setup(this);
        this.config.addDefaults();
        this.config.get().options().copyDefaults(true);
        this.config.save();
        logger.info("Initialized Files");

        getCommand("coffee").setExecutor(new CommandManager(this));

        initModules();
        logger.info("Initialized Modules");

        initTasks();
        logger.info("Initialized Tasks");

        initListeners();
        logger.info("Initialized Listeners");

        logger.info("CoffeeCore has been loaded in " + test.getMillis() + "ms");
    }

    private void initModules() {

        this.modules.add(new AntiCrash(this));
        this.modules.add(new AntiBot(this));
        this.modules.add(new BreakAreas(this));
        this.modules.add(new WorldDownloader(this));
        this.modules.add(new ScaffoldAreas(this));
        this.modules.add(new NoHunger(this));
        this.modules.add(new CommandSigns(this));
        this.modules.add(new SpawnItems(this));
        this.modules.add(new VelocityMobs(this));

        this.modules.forEach(Module::init);
    }

    public Profile getProfile(Player player) {
        return this.profiles.computeIfAbsent(player.getUniqueId(), Profile::new);
    }

    @Override
    public void onDisable() {

        this.modules.forEach(Module::disInit);

        this.modules.clear();

        this.config.reload();
        this.config.save();

        HandlerList.unregisterAll(this);

        this.getServer().getScheduler().cancelTasks(this);

        instance = null;
    }
}