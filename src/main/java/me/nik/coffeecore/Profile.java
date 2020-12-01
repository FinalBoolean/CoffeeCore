package me.nik.coffeecore;

import me.nik.coffeecore.utils.custom.CommandSign;
import me.nik.coffeecore.utils.custom.ScaffoldArea;

import java.util.UUID;

public class Profile {

    private final UUID uuid;

    private boolean scaffoldMode;
    private ScaffoldArea scaffoldArea;

    private boolean commandSignMode;
    private CommandSign commandSign;

    private long commandSignCooldown;

    public Profile(UUID uuid) {
        this.uuid = uuid;
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
}