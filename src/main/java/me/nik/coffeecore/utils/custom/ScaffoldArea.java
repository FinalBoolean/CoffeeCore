package me.nik.coffeecore.utils.custom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class ScaffoldArea {

    private Location one, two;

    public ScaffoldArea() {
    }

    public ScaffoldArea(String str) {
        if (str == null) return;
        String[] data = str.split(",");
        try {
            double xOne = Double.parseDouble(data[0]);
            double yOne = Double.parseDouble(data[1]);
            double zOne = Double.parseDouble(data[2]);
            World worldOne = Bukkit.getWorld(data[3]);
            this.one = new Location(worldOne, xOne, yOne, zOne);

            double xTwo = Double.parseDouble(data[4]);
            double yTwo = Double.parseDouble(data[5]);
            double zTwo = Double.parseDouble(data[6]);
            World worldTwo = Bukkit.getWorld(data[7]);
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

    public void clean() {
        int topBlockX = (Math.max(this.one.getBlockX(), this.two.getBlockX()));
        int bottomBlockX = (Math.min(this.one.getBlockX(), this.two.getBlockX()));

        int topBlockY = (Math.max(this.one.getBlockY(), this.two.getBlockY()));
        int bottomBlockY = (Math.min(this.one.getBlockY(), this.two.getBlockY()));

        int topBlockZ = (Math.max(this.one.getBlockZ(), this.two.getBlockZ()));
        int bottomBlockZ = (Math.min(this.one.getBlockZ(), this.two.getBlockZ()));

        for(int x = bottomBlockX; x <= topBlockX; x++) {
            for(int z = bottomBlockZ; z <= topBlockZ; z++) {
                for(int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = this.one.getWorld().getBlockAt(x, y, z);
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