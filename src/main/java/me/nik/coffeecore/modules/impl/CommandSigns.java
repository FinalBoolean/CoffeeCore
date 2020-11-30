package me.nik.coffeecore.modules.impl;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Profile;
import me.nik.coffeecore.managers.CommandSignManager;
import me.nik.coffeecore.modules.Module;
import me.nik.coffeecore.utils.Messenger;
import me.nik.coffeecore.utils.custom.CommandSign;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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
        if (!e.getAction().name().contains("RIGHT") || e.getClickedBlock().getType() != Material.valueOf("SIGN"))
            return;

        Player p = e.getPlayer();
        Profile profile = this.plugin.getProfileManager().getProfile(p);
        if (profile.getCommandSignCooldown() < 5000) {
            p.sendMessage(Messenger.PREFIX + "You must wait before using this Sign again");
            return;
        }

        Block block = e.getClickedBlock();

        for (CommandSign sign : this.commandSigns) {
            if (sign.getLocation().equals(block.getLocation())) {
                sign.execute(e.getPlayer());
                break;
            }
        }

        profile.addCommandSignCooldown();
    }

    @EventHandler
    public void onSign(final SignChangeEvent e) {
        Profile profile = this.plugin.getProfileManager().getProfile(e.getPlayer());
        if (!profile.isCommandSignMode()) return;

        CommandSign commandSign = profile.getCommandSign();
        commandSign.setLocation(e.getPlayer().getLocation());
        this.manager.addCommandSign(commandSign);
        profile.resetCommandSign();
        profile.setCommandSignMode(false);
        e.getPlayer().sendMessage(Messenger.PREFIX + "Command sign successfully set!");
    }
}