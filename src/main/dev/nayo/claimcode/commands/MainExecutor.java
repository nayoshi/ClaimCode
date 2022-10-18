package dev.nayo.claimcode.commands;

import dev.nayo.claimcode.ClaimCode;
import dev.nayo.claimcode.commands.sub.Claim;
import dev.nayo.claimcode.commands.sub.Reload;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class MainExecutor implements CommandExecutor {
    private HashMap<String, SubCommand> subcommands = new HashMap<>();
    private ClaimCode instance;
    private Claim claim;
    public MainExecutor() {
        instance = ClaimCode.getInstance();
        subcommands.put("reload", new Reload().setName("reload"));
        claim = new Claim();
    }

    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("claimcode")) {
            if (args.length == 0) {
                // Sends a thing for tsdjbklasdjvals
                return true;
            }else {
                if (subcommands.get(args[0]) == null) {
                    // Checks if argument is a valid command
                    claim.execute(cs, args);
                    return true;
                }
                subcommands.get(args[0]).execute(cs, args);
            }
        }
        return false;
    }
}
