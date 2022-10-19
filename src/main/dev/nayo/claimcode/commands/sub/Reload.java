package dev.nayo.claimcode.commands.sub;

import dev.nayo.claimcode.ClaimCode;
import dev.nayo.claimcode.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class Reload extends SubCommand {
    private ClaimCode instance;
    private String permission;
    public Reload() {
        instance = ClaimCode.getInstance();
        permission = "claimcode.reload";
    }
    public void execute(CommandSender cs, String[] args) {
        instance.reload();
        cs.sendMessage(instance.getHelper().print("Plugin Reloaded!"));
    }

    public String getPermission() {
        return permission;
    }

    public String getName() {
        return "reload";
    }
}
