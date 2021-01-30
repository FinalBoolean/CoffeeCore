package me.nik.coffeecore.commands;

import me.nik.coffeecore.CoffeeCore;
import me.nik.coffeecore.commands.subcommands.BreakCommand;
import me.nik.coffeecore.commands.subcommands.CommandSignCommand;
import me.nik.coffeecore.commands.subcommands.ReloadCommand;
import me.nik.coffeecore.commands.subcommands.ScaffoldCommand;
import me.nik.coffeecore.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {

    private final CoffeeCore plugin;

    private final List<SubCommand> subCommands = new ArrayList<>();

    public CommandManager(CoffeeCore plugin) {
        this.plugin = plugin;

        subCommands.add(new BreakCommand(plugin));
        subCommands.add(new ScaffoldCommand(plugin));
        subCommands.add(new ReloadCommand(plugin));
        subCommands.add(new CommandSignCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            for (int i = 0; i < getSubcommands().size(); i++) {
                final SubCommand subCommand = getSubcommands().get(i);
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    if (!subCommand.canConsoleExecute() && sender instanceof ConsoleCommandSender) {
                        sender.sendMessage(Messenger.PREFIX + "&cYou cannot use this command in the console");
                        return true;
                    }
                    if (!sender.hasPermission(subCommand.getPermission())) {
                        sender.sendMessage(Messenger.PREFIX + "&cYou're not a Coffee Lover...");
                        return true;
                    }
                    if (args.length < subCommand.maxArguments()) {
                        helpMessage(sender);
                        return true;
                    }
                    subCommand.perform(sender, args);
                    return true;
                }
                if (args[0].equalsIgnoreCase("help")) {
                    helpMessage(sender);
                    return true;
                }
            }
        } else {
            pluginInfo(sender);
            return true;
        }
        helpMessage(sender);
        return true;
    }

    public List<SubCommand> getSubcommands() {
        return subCommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            ArrayList<String> subcommandsArgs = new ArrayList<>();
            for (int i = 0; i < getSubcommands().size(); i++) {
                subcommandsArgs.add(getSubcommands().get(i).getName());
            }
            return subcommandsArgs;
        }
        return null;
    }

    private void helpMessage(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(Messenger.PREFIX + ChatColor.WHITE + "Available Commands");
        sender.sendMessage("");
        for (int i = 0; i < getSubcommands().size(); i++) {
            if (!sender.hasPermission(getSubcommands().get(i).getPermission())) continue;
            sender.sendMessage(ChatColor.GOLD + getSubcommands().get(i).getSyntax() + ChatColor.YELLOW + " - " + ChatColor.GOLD + getSubcommands().get(i).getDescription());
        }
        sender.sendMessage("");
    }

    private void pluginInfo(CommandSender sender) {
        sender.sendMessage(Messenger.PREFIX + Messenger.format("&6This server is running &f" + plugin.getDescription().getName() + " &6version &fv" + plugin.getDescription().getVersion() + " &6by &fNik"));
    }
}