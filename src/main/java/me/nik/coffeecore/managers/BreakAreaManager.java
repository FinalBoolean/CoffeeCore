package me.nik.coffeecore.managers;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.utils.FileBuilder;
import me.nik.coffeecore.utils.custom.BreakArea;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BreakAreaManager {

    private final FileBuilder fb;

    public BreakAreaManager(CoffeeCore plugin) {
        this.fb = new FileBuilder(plugin.getDataFolder().getPath(), "config.yml");
        fb.save();
    }

    public int addData(BreakArea data) {
        int id = getNewID();
        fb.setValue("locations.break_areas." + id, data.toString());
        fb.save();
        return id;
    }

    public List<BreakArea> getBreakAreas() {
        List<BreakArea> data = new ArrayList<>();

        if (this.fb.getConfiguration().getConfigurationSection("locations.break_areas") == null) return data;

        for (String id : this.fb.getConfiguration().getConfigurationSection("locations.break_areas").getKeys(false)) {
            data.add(new BreakArea(this.fb.getConfiguration().getString("locations.break_areas." + id)));
        }

        return data;
    }

    private int getNewID() {
        int id = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        if (fb.exist() && fb.getConfiguration().isSet("locations.break_areas." + id)) {
            id = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            return id;
        }
        return id;
    }
}