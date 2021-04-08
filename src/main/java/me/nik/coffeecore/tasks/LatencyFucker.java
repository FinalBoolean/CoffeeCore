package me.nik.coffeecore.tasks;

import me.nik.coffeecore.utils.PlayerUtils;
import me.nik.coffeecore.utils.TaskUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

public class LatencyFucker extends BukkitRunnable {

    private static final String KICK_MESSAGE = "Someone forgot to pay the bill for the internet this month";

    @Override
    public void run() {

        final List<Player> toKick = Bukkit.getOnlinePlayers().stream().filter(player ->
                PlayerUtils.getNmsPing(player) > 1250).collect(Collectors.toList());

        if (toKick.isEmpty()) return;

        TaskUtils.task(() -> toKick.forEach(player -> player.kickPlayer(KICK_MESSAGE)));
    }
}