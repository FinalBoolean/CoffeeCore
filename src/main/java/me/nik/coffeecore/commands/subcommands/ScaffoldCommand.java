package me.nik.coffeecore.commands.subcommands;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Permissions;
import me.nik.coffeecore.Profile;
import me.nik.coffeecore.commands.SubCommand;
import me.nik.coffeecore.utils.CoffeeUtils;
import me.nik.coffeecore.utils.Messenger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ScaffoldCommand extends SubCommand {

    private final CoffeeCore plugin;

    public ScaffoldCommand(CoffeeCore plugin) {
        this.plugin = plugin;
    }

    @Override
    protected String getName() {
        return "scaffold";
    }

    @Override
    protected String getDescription() {
        return "Create a new scaffold area";
    }

    @Override
    protected String getSyntax() {
        return "/coffee scaffold";
    }

    @Override
    protected String getPermission() {
        return Permissions.ADMIN.getPermission();
    }

    @Override
    protected int maxArguments() {
        return 1;
    }

    @Override
    protected boolean canConsoleExecute() {
        return false;
    }

    @Override
    protected void perform(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        Profile profile = this.plugin.getProfile(p);

        if (profile.isScaffoldMode()) {
            profile.setScaffoldMode(false);
            profile.resetScaffoldArea();
            profile.getPlayer().getInventory().remove(CoffeeUtils.scaffoldAreaItem());
            sender.sendMessage(Messenger.PREFIX + "You have cancelled the scaffold area mode");
        } else {
            profile.setScaffoldMode(true);
            sender.sendMessage(Messenger.PREFIX + "You have enabled the area scaffold mode, type `Done` in chat once you're finished, `Cancel` to cancel");
            p.getInventory().addItem(CoffeeUtils.scaffoldAreaItem());
        }
    }

    @Override
    protected List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}