package me.nik.coffeecore.utils.custom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ScaffoldArea {

    private Location one, two;

    public ScaffoldArea() {
    }

    public ScaffoldArea(String str) {
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

    public boolean isNearArea(Player player) {
        //This is bad, and could be better.

        if (player.getWorld() != this.one.getWorld()) return false;

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

    public void setOne(Location one) {
        this.one = one;
    }

    public void setTwo(Location two) {
        this.two = two;
    }

    public void clean() {

        final int topBlockX = (Math.max(this.one.getBlockX(), this.two.getBlockX()));
        final int bottomBlockX = (Math.min(this.one.getBlockX(), this.two.getBlockX()));

        final int topBlockY = (Math.max(this.one.getBlockY(), this.two.getBlockY()));
        final int bottomBlockY = (Math.min(this.one.getBlockY(), this.two.getBlockY()));

        final int topBlockZ = (Math.max(this.one.getBlockZ(), this.two.getBlockZ()));
        final int bottomBlockZ = (Math.min(this.one.getBlockZ(), this.two.getBlockZ()));

        final World world = this.one.getWorld();

        for (int x = bottomBlockX; x <= topBlockX; x++) {

            for (int z = bottomBlockZ; z <= topBlockZ; z++) {

                for (int y = bottomBlockY; y <= topBlockY; y++) {

                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() == Material.AIR) continue;
                    block.setType(Material.AIR);
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