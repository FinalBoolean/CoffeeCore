package me.nik.coffeecore.managers;

import me.nik.coffeecore.Profile;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager {

    private final Map<UUID, Profile> profiles = new HashMap<>();

    public void addProfile(Player player) {
        if (player == null) return;

        UUID uuid = player.getUniqueId();

        if (this.profiles.containsKey(uuid)) return;

        this.profiles.put(uuid, new Profile(uuid));
    }

    public void removeProfile(Player player) {
        UUID uuid = player.getUniqueId();

        if (!this.profiles.containsKey(uuid)) return;

        this.profiles.remove(player.getUniqueId());
    }

    public Profile getProfile(OfflinePlayer player) {
        UUID uuid = player.getUniqueId();

        if (!this.profiles.containsKey(uuid)) this.profiles.put(uuid, new Profile(uuid));

        return this.profiles.get(player.getUniqueId());
    }

    public Map<UUID, Profile> getProfileMap() {
        return this.profiles;
    }
}