package me.nik.coffeecore.utils.custom;

import me.nik.coffeecore.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VelocityMob {

    private static final PotionEffect SLOWNESS = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 10);
    private static final String NAME = Messenger.format("&b&lVelocity");

    private Location location;

    private Entity entity;

    public VelocityMob(String str) {
        if (str == null) return;

        final String[] data = str.split(",");

        try {
            final double x = Double.parseDouble(data[0]);
            final double y = Double.parseDouble(data[1]);
            final double z = Double.parseDouble(data[2]);
            final World world = Bukkit.getWorld(data[3]);

            this.location = new Location(world, x, y, z);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public VelocityMob() {
    }

    public Entity getEntity() {
        return entity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location.add(0, 2, 0);
    }

    public void spawn() {

        if (this.location == null) return;

        boolean loaded = true;

        if (!this.location.getWorld().isChunkLoaded(this.location.getBlockX() >> 4, this.location.getBlockZ() >> 4)) {
            loaded = this.location.getChunk().load();
        }

        if (!loaded) throw new CoffeeException("Couldn't load chunk at " + toString());

        Zombie zombie = (Zombie) this.location.getWorld().spawnEntity(this.location, EntityType.ZOMBIE);

        zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));

        zombie.setCanPickupItems(false);

        zombie.addPotionEffect(SLOWNESS);

        zombie.setCustomName(NAME);

        this.entity = zombie;
    }

    public void kill() {

        if (this.location == null) return;

        this.entity.remove();
    }

    @Override
    public String toString() {
        return this.location.getX() + ","
                + this.location.getY()
                + "," + this.location.getZ()
                + "," + this.location.getWorld().getName();
    }
}