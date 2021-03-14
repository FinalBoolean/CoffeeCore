package me.nik.coffeecore.modules.impl.anticrash.checks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;

public abstract class CrashCheck {

    public abstract boolean handle(final PacketEvent e);

    protected boolean isFlying(final PacketEvent e) {

        final PacketType type = e.getPacketType();

        return type == PacketType.Play.Client.FLYING
                || type == PacketType.Play.Client.POSITION
                || type == PacketType.Play.Client.POSITION_LOOK
                || type == PacketType.Play.Client.LOOK;
    }
}