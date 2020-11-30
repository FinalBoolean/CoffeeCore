package me.nik.coffeecore;

import me.nik.coffeecore.utils.custom.ScaffoldAreaData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Profile {

    private final UUID uuid;

    private boolean scaffoldMode;
    private ScaffoldAreaData scaffoldAreaData;

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isScaffoldMode() {
        return scaffoldMode;
    }

    public void setScaffoldMode(boolean scaffoldMode) {
        this.scaffoldMode = scaffoldMode;
    }

    public ScaffoldAreaData getScaffoldAreaData() {
        if (this.scaffoldAreaData == null) this.scaffoldAreaData = new ScaffoldAreaData();
        return scaffoldAreaData;
    }

    public void resetScaffoldAreaData() {
        this.scaffoldAreaData = null;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }
}