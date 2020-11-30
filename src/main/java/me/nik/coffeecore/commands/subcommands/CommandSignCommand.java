package me.nik.coffeecore.commands.subcommands;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.Permissions;
import me.nik.coffeecore.Profile;
import me.nik.coffeecore.commands.SubCommand;
import me.nik.coffeecore.utils.CoffeeUtils;
import me.nik.coffeecore.utils.Messenger;
import me.nik.coffeecore.utils.custom.CommandSign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandSignCommand extends SubCommand {

    private final CoffeeCore plugin;

    public CommandSignCommand(CoffeeCore plugin) {
        this.plugin = plugin;
    }

    @Override
    protected String getName() {
        return "commandsign";
    }

    @Override
    protected String getDescription() {
        return "Place a command sign, Placeholders: %player% (console only)";
    }

    @Override
    protected String getSyntax() {
        return "/coffee commandsign <console> <command>";
    }

    @Override
    protected String getPermission() {
        return Permissions.ADMIN.getPermission();
    }

    @Override
    protected int maxArguments() {
        return 3;
    }

    @Override
    protected boolean canConsoleExecute() {
        return false;
    }

    @Override
    protected void perform(CommandSender sender, String[] args) {

        StringBuilder commandBuilder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            commandBuilder.append(args[i]).append(" ");
        }
        final String command = commandBuilder.toString().trim();

        boolean console = Boolean.parseBoolean(args[1]);

        Player p = (Player) sender;
        Profile profile = this.plugin.getProfileManager().getProfile(p);

        if (profile.isCommandSignMode()) {
            profile.setCommandSignMode(false);
            profile.resetCommandSign();
            sender.sendMessage(Messenger.PREFIX + "You have cancelled the command sign mode");
            if (p.getInventory().contains(CoffeeUtils.commandSignItem()))
                p.getInventory().remove(CoffeeUtils.commandSignItem());
        } else {
            profile.setCommandSignMode(true);
            sender.sendMessage(Messenger.PREFIX + "You have enabled the command sign mode, Place the sign anywhere you like");
            p.getInventory().addItem(CoffeeUtils.commandSignItem());

            profile.setCommandSign(new CommandSign(console, command));
        }
    }

    @Override
    protected List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}