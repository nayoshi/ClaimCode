package dev.nayo.claimcode.commands.sub;

import dev.nayo.claimcode.ClaimCode;
import dev.nayo.claimcode.CodeInstance;
import dev.nayo.claimcode.commands.MainExecutor;
import dev.nayo.claimcode.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Test extends SubCommand {
    public ClaimCode instance;
    private String permission;
    private Claim claim;
    public Test(Claim claim) {
        instance = ClaimCode.getInstance();
        permission = "claimcode.test";
        this.claim = claim;
    }
    public void execute(CommandSender cs, String[] args) {
        if (!(cs instanceof Player)) {
            instance.getHelper().sendMessage(cs, "Can't execute this command on console, you must be logged on.");
            return;
        }
        if (args.length < 2) {
            instance.getHelper().sendMessage(cs, MainExecutor.getInvalidCommand());
            return;
        }
        CodeInstance code = instance.getCodeManager().getCodeInstanceFromString(args[1]);
        if (code == null) {
            instance.getHelper().sendMessage(cs, "Invalid key, check config for the correct key. This is case-insenstive so you misspeleed it.");
            return;
        }
        claim.redeem((Player) cs, code);
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public List<String> getArgument(int idx) {
        if (idx == 2) {
            return instance.getCodeManager().getAllKeys();
        }
        return null;
    }

    public String getName() {
        return "test";
    }
}
