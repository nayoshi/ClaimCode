package dev.nayo.claimcode.commands;

import org.bukkit.command.CommandSender;

abstract public class SubCommand {
    abstract public void execute(CommandSender cs, String[] args);
    abstract public String getName();
}
