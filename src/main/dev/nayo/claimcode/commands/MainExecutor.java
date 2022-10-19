package dev.nayo.claimcode.commands;

import dev.nayo.claimcode.ClaimCode;
import dev.nayo.claimcode.commands.sub.Claim;
import dev.nayo.claimcode.commands.sub.Reload;
import dev.nayo.claimcode.commands.sub.Reset;
import dev.nayo.claimcode.commands.sub.Test;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainExecutor implements CommandExecutor {
    private static HashMap<String, SubCommand> subcommands = new HashMap<>();
    private ClaimCode instance;
    private Claim claim;
    public MainExecutor() {
        instance = ClaimCode.getInstance();
        subcommands.put("reload", new Reload());
        claim = new Claim();
        subcommands.put("test", new Test(claim));
        subcommands.put("reset", new Reset());
    }

    public static List<String> getCommands(CommandSender cs) {
        List<String> subbies = new ArrayList<>();

        for (String s : subcommands.keySet()) {
            if (cs.hasPermission(subcommands.get(s).getPermission())) {
                subbies.add(s);
            }
        }
        return subbies;
    }
    public static List<String> getSubCommand(CommandSender cs, String[] args) {
        SubCommand cmd = subcommands.get(args[0]);
        if (cmd == null) {
            return null;
        }
        return cmd.getArgument(args.length);
    }
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("claimcode")) {
            if (args.length == 0) {
                // Sends a thing for tsdjbklasdjvals

                StringBuilder stringBuilder = new StringBuilder("&6ClaimCode &aPlugin is created by nayoshi12.\n");
                stringBuilder.append("The purpose is to claim any promotional codes that is distributed in and outside of the server\n");
                stringBuilder.append("To get started, please type &c/claimcode <validcode>&a. (Of course insert an actual valid code)\n");

                StringBuilder commands = new StringBuilder("&cCommands\n");
                boolean access = false;
                for (String sub : subcommands.keySet()) {
                    if (cs.hasPermission(subcommands.get(sub).getPermission())) {
                        access = true;
                        commands.append("&2/claimcode " + sub+"\n");
                    }
                }
                if (access) {
                    stringBuilder.append(commands);
                    stringBuilder.append("&a&lFollow the goshdarn autocomplete, I worked so hard on it, please use it T^T");
                }
                instance.getHelper().sendMessage(cs, stringBuilder.toString());
                return true;
            }
            String root = args[0].toLowerCase();
            if (subcommands.get(root) == null) {
                if (instance.isDisabled()) {
                    instance.getHelper().sendMessage(cs, "Plugin is currently disabled, please verify everything is correct in the config, and make changes. Then do /claimcode reload to enable it again.");
                    return true;
                }
                claim.execute(cs, args);
                return true;
            }

            SubCommand sub = subcommands.get(root);
            if (!cs.hasPermission(sub.getPermission())){
                instance.getHelper().sendMessage(cs, sub.getRejectError());
                return true;
            }
            subcommands.get(root).execute(cs, args);

        }
        return false;
    }
    public static String getInvalidCommand() {
        return "Command/Argument is invalid. Please follow the autocomplete. I worked so hard on it :( -nayo";
    }
    public static String getInvalidMessage() {
        return "This code is invalid. Please double check your spelling.";
    }
    public static String getDisabledMessage() {
        return "This code has been disabled. Try another code.";
    }
    public static String getRedeemedMessage() {
        return "You have claimed this code or a similar code in the past.";
    }
}
