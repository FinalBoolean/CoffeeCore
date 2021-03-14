package me.nik.coffeecore.modules.impl.anticrash.checks.impl;

import com.comphenix.protocol.events.PacketEvent;
import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Profile;
import me.nik.coffeecore.modules.impl.anticrash.checks.CrashCheck;

public class Limit extends CrashCheck {

    @Override
    public boolean handle(PacketEvent e) {

        Profile profile = CoffeeCore.getInstance().getProfile(e.getPlayer());

        return profile.getPacketBucket().increment();
    }
}