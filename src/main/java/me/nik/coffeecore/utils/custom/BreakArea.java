package me.nik.coffeecore.utils.custom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class BreakArea {
    private Location one, two;

    public BreakArea() {
    }

    public BreakArea(String str) {
        if (str == null) return;

        final String[] data = str.split(",");

        try {
            final double xOne = Double.parseDouble(data[0]);
            final double yOne = Double.parseDouble(data[1]);
            final double zOne = Double.parseDouble(data[2]);
            final World worldOne = Bukkit.getWorld(data[3]);

            this.one = new Location(worldOne, xOne, yOne, zOne);

            final double xTwo = Double.parseDouble(data[4]);
            final double yTwo = Double.parseDouble(data[5]);
            final double zTwo = Double.parseDouble(data[6]);
            final World worldTwo = Bukkit.getWorld(data[7]);

            this.two = new Location(worldTwo, xTwo, yTwo, zTwo);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void setOne(Location one) {
        this.one = one;
    }

    public void setTwo(Location two) {
        this.two = two;
    }

    public boolean isNearArea(Player player) {
        //This is bad, and could be better.

        final int topBlockX = (Math.max(this.one.getBlockX(), this.two.getBlockX()));
        final int bottomBlockX = (Math.min(this.one.getBlockX(), this.two.getBlockX()));

        final int topBlockY = (Math.max(this.one.getBlockY(), this.two.getBlockY()));
        final int bottomBlockY = (Math.min(this.one.getBlockY(), this.two.getBlockY()));

        final int topBlockZ = (Math.max(this.one.getBlockZ(), this.two.getBlockZ()));
        final int bottomBlockZ = (Math.min(this.one.getBlockZ(), this.two.getBlockZ()));

        final World world = this.one.getWorld();

        Location location = new Location(world, 0, 0, 0);

        final Location playerLocation = player.getLocation();

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {

                    //Better than creating a new location variable every time.
                    location.setX(x);
                    location.setY(y);
                    location.setZ(z);

                    if (playerLocation.distanceSquared(location) < 3) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void rebuild() {

        final int topBlockX = (Math.max(this.one.getBlockX(), this.two.getBlockX()));
        final int bottomBlockX = (Math.min(this.one.getBlockX(), this.two.getBlockX()));

        final int topBlockY = (Math.max(this.one.getBlockY(), this.two.getBlockY()));
        final int bottomBlockY = (Math.min(this.one.getBlockY(), this.two.getBlockY()));

        final int topBlockZ = (Math.max(this.one.getBlockZ(), this.two.getBlockZ()));
        final int bottomBlockZ = (Math.min(this.one.getBlockZ(), this.two.getBlockZ()));

        final World world = this.one.getWorld();

        Location location = new Location(world, 0, 0, 0);

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {

                    //Better than creating a new location variable every time.
                    location.setX(x);
                    location.setY(y);
                    location.setZ(z);

                    //Yes i know this is heavy and could probably be better, But lazyness is strong!
                    //This will probably show high on timings, Sorry me!
                    if (world.getEntitiesByClass(Player.class)
                            .stream().anyMatch(player ->
                                    player != null
                                            && !player.hasMetadata("NPC")
                                            && player.getLocation().distanceSquared(location) < 5)) {
                        continue;
                    }

                    world.getBlockAt(x, y, z).setType(Material.STONE);
                }
            }
        }
    }

    @Override
    public String toString() {
        return this.one.getX() + "," +
                this.one.getY() + "," +
                this.one.getZ() + "," +
                this.one.getWorld().getName() +
                "," + this.two.getX() +
                "," + this.two.getY() +
                "," + this.two.getZ() +
                "," + this.two.getWorld().getName();
    }
}