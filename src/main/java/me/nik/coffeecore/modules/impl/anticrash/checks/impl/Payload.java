package me.nik.coffeecore.modules.impl.anticrash.checks.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Profile;
import me.nik.coffeecore.modules.impl.anticrash.checks.CrashCheck;

public class Payload extends CrashCheck {
    @Override
    public boolean handle(PacketEvent e) {
        if (e.getPacketType() != PacketType.Play.Client.CUSTOM_PAYLOAD) return false;

        String info;

        try {
            info = e.getPacket().getStrings().readSafely(0).toLowerCase();
        } catch (NullPointerException ignored) {
            return false;
        }

        if (info.contains("bungeecord") || info.contains("geyser") || info.contains("itemname")) return false;

        Profile profile = CoffeeCore.getInstance().getProfile(e.getPlayer());

        final long elapsed = profile.getLastCustomPayload();

        profile.updateLastCustomPayload();

        if (elapsed <= 50L) {
            return profile.increaseCustomPayloadBuffer(1) >= 3;
        } else profile.resetCustomPayloadBuffer();

        return false;
    }
}