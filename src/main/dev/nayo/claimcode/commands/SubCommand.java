package dev.nayo.claimcode.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

abstract public class SubCommand {
    abstract public void execute(CommandSender cs, String[] args);
    abstract public String getName();
    abstract public String getPermission();
    public String getRejectError() {
        return "You don't have permission to use this command!";
    }
    public List<String> getArgument(int idx) {
        List<String> str = new ArrayList<>();
        return str;
    }
}
