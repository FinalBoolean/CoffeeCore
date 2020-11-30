package me.nik.coffeecore.commands.subcommands;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Permissions;
import me.nik.coffeecore.commands.SubCommand;
import me.nik.coffeecore.utils.Messenger;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand extends SubCommand {

    private final CoffeeCore plugin;

    public ReloadCommand(CoffeeCore plugin) {
        this.plugin = plugin;
    }

    @Override
    protected String getName() {
        return "reload";
    }

    @Override
    protected String getDescription() {
        return "Reload the Coffee Core";
    }

    @Override
    protected String getSyntax() {
        return "/coffee reload";
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
        return true;
    }

    @Override
    protected void perform(CommandSender sender, String[] args) {
        this.plugin.onDisable();
        this.plugin.onEnable();
        sender.sendMessage(Messenger.PREFIX + "You have successfully reloaded the Coffee Core!");
    }

    @Override
    protected List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}