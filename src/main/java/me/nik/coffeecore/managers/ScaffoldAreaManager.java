package me.nik.coffeecore.managers;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.utils.FileBuilder;
import me.nik.coffeecore.utils.custom.ScaffoldAreaData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ScaffoldAreaManager {

    private final FileBuilder fb;

    public ScaffoldAreaManager() {
        this.fb = new FileBuilder(CoffeeCore.getInstance().getDataFolder().getPath(), "config.yml");
        fb.save();
    }

    public int addData(ScaffoldAreaData data) {
        int id = getNewID();
        fb.setValue("locations.scaffold_areas." + id, data.toString());
        fb.save();
        return id;
    }

    public List<ScaffoldAreaData> getData() {
        List<ScaffoldAreaData> data = new ArrayList<>();

        if (this.fb.getConfiguration().getConfigurationSection("locations.scaffold_areas") == null) return data;

        for (String id : this.fb.getConfiguration().getConfigurationSection("locations.scaffold_areas").getKeys(false)) {
            data.add(new ScaffoldAreaData(this.fb.getConfiguration().getString("locations.scaffold_areas." + id)));
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