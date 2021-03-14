package me.nik.coffeecore.modules.impl.anticrash.checks.impl;

import com.comphenix.protocol.events.PacketEvent;
import me.nik.coffeecore.modules.impl.anticrash.checks.CrashCheck;
import me.nik.coffeecore.wrappers.WrapperPlayClientInstanceOfFlying;

import java.util.function.Predicate;

public class Pos extends CrashCheck {

    private final Predicate<Double> invalidDouble = value -> Double.isNaN(value)
            || Double.isInfinite(value)
            || value == Double.MAX_VALUE
            || value == Double.MIN_VALUE;

    private final Predicate<Float> invalidFloat = value -> Float.isNaN(value)
            || Float.isInfinite(value)
            || value == Float.MAX_VALUE
            || value == Float.MIN_VALUE;

    @Override
    public boolean handle(PacketEvent e) {
        if (!isFlying(e)) return false;

        WrapperPlayClientInstanceOfFlying packet = new WrapperPlayClientInstanceOfFlying(e.getPacket());

        final double x = packet.getX();
        final double y = packet.getY();
        final double z = packet.getZ();

        final float yaw = packet.getYaw();
        final float pitch = packet.getPitch();

        position:
        {

            if (x == 0 || y == 0 || z == 0) break position;

            if (this.invalidDouble.test(x) || this.invalidDouble.test(y) || this.invalidDouble.test(z)) return true;
        }

        rotation:
        {

            if (yaw == 0 || pitch == 0) break rotation;

            if (this.invalidFloat.test(yaw) || this.invalidFloat.test(pitch)) return true;
        }

        return false;
    }
}