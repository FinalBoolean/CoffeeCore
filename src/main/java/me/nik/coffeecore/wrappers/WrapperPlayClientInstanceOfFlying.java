package me.nik.coffeecore.wrappers;

import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayClientInstanceOfFlying extends AbstractPacket {

    public WrapperPlayClientInstanceOfFlying(PacketContainer packet) {
        super(packet, packet.getType());
    }

    public double getX() {
        return handle.getDoubles().readSafely(0);
    }

    public double getY() {
        return handle.getDoubles().readSafely(1);
    }

    public double getZ() {
        return handle.getDoubles().readSafely(2);
    }

    public float getYaw() {
        return handle.getFloat().readSafely(0);
    }

    public float getPitch() {
        return handle.getFloat().readSafely(1);
    }

    public boolean getOnGround() {
        return handle.getBooleans().readSafely(0);
    }
}