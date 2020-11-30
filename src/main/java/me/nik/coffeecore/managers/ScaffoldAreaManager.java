package me.nik.coffeecore.managers;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.utils.FileBuilder;
import me.nik.coffeecore.utils.custom.ScaffoldArea;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ScaffoldAreaManager {

    private final FileBuilder fb;

    public ScaffoldAreaManager(CoffeeCore plugin) {
        this.fb = new FileBuilder(plugin.getDataFolder().getPath(), "config.yml");
        fb.save();
    }

    public int addData(ScaffoldArea data) {
        int id = getNewID();
        fb.setValue("locations.scaffold_areas." + id, data.toString());
        fb.save();
        return id;
    }

    public List<ScaffoldArea> getScaffoldAreas() {
        List<ScaffoldArea> data = new ArrayList<>();

        if (this.fb.getConfiguration().getConfigurationSection("locations.scaffold_areas") == null) return data;

        for (String id : this.fb.getConfiguration().getConfigurationSection("locations.scaffold_areas").getKeys(false)) {
            data.add(new ScaffoldArea(this.fb.getConfiguration().getString("locations.scaffold_areas." + id)));
        }

        return data;
    }

    private int getNewID() {
        int id = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        if (fb.exist() && fb.getConfiguration().isSet("locations.scaffold_areas." + id)) {
            id = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            return id;
        }
        return id;
    }
}