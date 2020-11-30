package me.nik.coffeecore.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class FileBuilder {
    private final File file;

    private final YamlConfiguration configuration;

    public FileBuilder(String FilePath, String FileName) {
        this.file = new File(FilePath, FileName);
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void setValue(String ValuePath, Object Value) {
        this.configuration.set(ValuePath, Value);
    }

    public boolean exist() {
        return this.file.exists();
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}