package me.nik.coffeecore;

import me.nik.coffeecore.utils.custom.BreakArea;
import me.nik.coffeecore.utils.custom.CommandSign;
import me.nik.coffeecore.utils.custom.ScaffoldArea;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Profile {

    private final UUID uuid;

    private boolean scaffoldMode, breakMode;
    private ScaffoldArea scaffoldArea;
    private BreakArea breakArea;

    private boolean commandSignMode;
    private CommandSign commandSign;

    private long commandSignCooldown;

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public boolean isScaffoldMode() {
        return scaffoldMode;
    }

    public void setScaffoldMode(boolean scaffoldMode) {
        this.scaffoldMode = scaffoldMode;
    }

    public ScaffoldArea getScaffoldArea() {
        if (this.scaffoldArea == null) this.scaffoldArea = new ScaffoldArea();
        return scaffoldArea;
    }

    public boolean isBreakMode() {
        return breakMode;
    }

    public void setBreakMode(boolean breakMode) {
        this.breakMode = breakMode;
    }

    public BreakArea getBreakArea() {
        if (this.breakArea == null) this.breakArea = new BreakArea();
        return breakArea;
    }

    public boolean isCommandSignMode() {
        return commandSignMode;
    }

    public void setCommandSignMode(boolean commandSignMode) {
        this.commandSignMode = commandSignMode;
    }

    public CommandSign getCommandSign() {
        if (this.commandSign == null) this.commandSign = new CommandSign();

        return commandSign;
    }

    public void setCommandSign(CommandSign commandSign) {
        this.commandSign = commandSign;
    }

    public void resetCommandSign() {
        this.commandSign = null;
    }

    public void addCommandSignCooldown() {
        this.commandSignCooldown = System.currentTimeMillis();
    }

    public long getCommandSignCooldown() {
        return System.currentTimeMillis() - this.commandSignCooldown;
    }

    public void resetScaffoldArea() {
        this.scaffoldArea = null;
    }

    public void resetBreakArea() {
        this.breakArea = null;
    }
}