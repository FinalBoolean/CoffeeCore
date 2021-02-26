package me.nik.coffeecore.managers;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.utils.FileBuilder;
import me.nik.coffeecore.utils.custom.VelocityMob;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VelocityMobManager {

    private final FileBuilder fb;

    public VelocityMobManager(CoffeeCore plugin) {
        this.fb = new FileBuilder(plugin.getDataFolder().getPath(), "config.yml");
        fb.save();
    }

    public int addData(VelocityMob data) {
        int id = getNewID();
        fb.setValue("locations.velocity_mobs." + id, data.toString());
        fb.save();
        return id;
    }

    public List<VelocityMob> getVelocityMobs() {
        List<VelocityMob> data = new LinkedList<>();

        if (this.fb.getConfiguration().getConfigurationSection("locations.velocity_mobs") == null) return data;

        for (String id : this.fb.getConfiguration().getConfigurationSection("locations.velocity_mobs").getKeys(false)) {
            data.add(new VelocityMob(this.fb.getConfiguration().getString("locations.velocity_mobs." + id)));
        }

        return data;
    }

    private int getNewID() {
        int id = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        if (fb.exist() && fb.getConfiguration().isSet("locations.velocity_mobs." + id)) {
            id = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            return id;
        }
        return id;
    }
}