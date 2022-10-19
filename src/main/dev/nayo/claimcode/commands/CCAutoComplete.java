package dev.nayo.claimcode.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class CCAutoComplete implements TabCompleter {
    public List<String> onTabComplete(CommandSender cs, Command cmd, String s, String[] args) {
        if (args.length <= 1) {
            return MainExecutor.getCommands(cs);
        } else {
            return MainExecutor.getSubCommand(cs, args);
        }
    }
}
