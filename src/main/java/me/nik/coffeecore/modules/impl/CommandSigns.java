package me.nik.coffeecore.modules.impl;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Profile;
import me.nik.coffeecore.managers.CommandSignManager;
import me.nik.coffeecore.modules.Module;
import me.nik.coffeecore.utils.Messenger;
import me.nik.coffeecore.utils.custom.CommandSign;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandSigns extends Module {
    private final CommandSignManager manager = new CommandSignManager(this.plugin);
    private final List<CommandSign> commandSigns = new ArrayList<>();

    public CommandSigns(CoffeeCore plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        this.commandSigns.addAll(this.manager.getCommandSigns());
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK || !e.getClickedBlock().getType().name().contains("SIGN"))
            return;

        Player p = e.getPlayer();
        Profile profile = this.plugin.getProfileManager().getProfile(p);

        Location block = e.getClickedBlock().getLocation();

        for (CommandSign sign : this.commandSigns) {
            if (sign.getLocation().equals(block)) {
                if (profile.getCommandSignCooldown() < 2000) {
                    p.sendMessage(Messenger.PREFIX + "You must wait before using this Sign again");
                    break;
                }
                sign.execute(e.getPlayer());
                profile.addCommandSignCooldown();
                break;
            }
        }
    }

    @EventHandler
    public void onSign(final SignChangeEvent e) {
        Profile profile = this.plugin.getProfileManager().getProfile(e.getPlayer());
        if (!profile.isCommandSignMode()) return;

        CommandSign commandSign = profile.getCommandSign();
        commandSign.setLocation(e.getBlock().getLocation());
        this.commandSigns.add(commandSign);
        this.manager.addCommandSign(commandSign);
        profile.resetCommandSign();
        profile.setCommandSignMode(false);
        e.getPlayer().sendMessage(Messenger.PREFIX + "Command sign successfully set!");
    }
}