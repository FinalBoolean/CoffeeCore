package me.nik.coffeecore.utils.custom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CommandSign {

    private String command;

    private boolean console;

    private Location location;

    public CommandSign() {
    }

    public CommandSign(boolean console, String command) {
        this.console = console;
        this.command = command;
    }

    public CommandSign(String str) {
        if (str == null) return;
        String[] data = str.split(",");
        try {
            double x = Double.parseDouble(data[0]);
            double y = Double.parseDouble(data[1]);
            double z = Double.parseDouble(data[2]);
            World world = Bukkit.getWorld(data[3]);
            this.location = new Location(world, x, y, z);

            String command = data[4];
            boolean console = Boolean.parseBoolean(data[5]);
            this.command = command;
            this.console = console;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void execute(Player player) {
        if (this.console) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.command.replace("%player%", player.getName()));
        } else player.performCommand(command);
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return this.location.getX() + "," +
                this.location.getY() + "," +
                this.location.getZ() + "," +
                this.location.getWorld().getName() + "," +
                this.command + "," +
                this.console;
    }
}