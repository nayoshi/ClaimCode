package dev.nayo.claimcode.commands.sub;

import dev.nayo.claimcode.ClaimCode;
import dev.nayo.claimcode.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class Reload extends SubCommand {
    private ClaimCode instance;
    private String name;

    public Reload() {
        instance = ClaimCode.getInstance();
    }
    public void execute(CommandSender cs, String[] args) {

    }

    public SubCommand setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }
}
