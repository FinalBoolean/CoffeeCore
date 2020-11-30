package me.nik.coffeecore.managers;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.utils.FileBuilder;
import me.nik.coffeecore.utils.custom.CommandSign;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CommandSignManager {

    private final FileBuilder fb;

    public CommandSignManager(CoffeeCore plugin) {
        this.fb = new FileBuilder(plugin.getDataFolder().getPath(), "config.yml");
        fb.save();
    }

    public int addCommandSign(CommandSign sign) {
        int id = getNewID();
        fb.setValue("locations.command_signs." + id, sign.toString());
        fb.save();
        return id;
    }

    public List<CommandSign> getCommandSigns() {
        List<CommandSign> data = new ArrayList<>();

        if (this.fb.getConfiguration().getConfigurationSection("locations.command_signs") == null) return data;

        for (String id : this.fb.getConfiguration().getConfigurationSection("locations.command_signs").getKeys(false)) {
            data.add(new CommandSign(this.fb.getConfiguration().getString("locations.command_signs." + id)));
        }

        return data;
    }

    private int getNewID() {
        int id = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        if (fb.exist() && fb.getConfiguration().isSet("locations.command_signs." + id)) {
            id = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            return id;
        }
        return id;
    }
}