package dev.nayo.claimcode.commands.sub;

import dev.nayo.claimcode.ClaimCode;
import dev.nayo.claimcode.CodeInstance;
import dev.nayo.claimcode.CodeManager;
import dev.nayo.claimcode.commands.MainExecutor;
import dev.nayo.claimcode.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Claim extends SubCommand {
    private ClaimCode instance;
    private CodeManager codeManager;
    public Claim(){
        instance = ClaimCode.getInstance();
        codeManager = instance.getCodeManager();
    }

    public void execute(CommandSender cs, String[] args) {
        String codeName = args[0].toLowerCase();
        CodeInstance code = codeManager.getCodeInstanceFromString(codeName);
        if (!(cs instanceof Player)) {
            instance.getHelper().sendMessage(cs, "Can't execute this command on console, you must be logged on.");
            return;
        }
        if (args.length > 1 || code == null){
            cs.sendMessage(instance.getHelper().print(MainExecutor.getInvalidMessage()));
            return;
        }
        if (!code.isEnabled()) {
            cs.sendMessage(instance.getHelper().print(MainExecutor.getDisabledMessage()));
            return;
        }
        Player player = (Player) cs;

        List<String> blacklist = code.getBlacklists();
        List<String> searching = new ArrayList<>();
        searching.add(code.getName());
        searching.addAll(blacklist);
        CompletableFuture<Boolean> cf = new CompletableFuture<>();
        instance.useDatabase().canRedeem(searching, player, cf);
        cf.whenComplete((res, err)->{
            if (err!=null) err.printStackTrace();
            if (res) {
                Bukkit.getScheduler().runTask(instance, ()->{
                    redeem(player, code);
                    instance.useDatabase().setRedeemed(code.getName(), player);
                });
            } else{
                player.sendMessage(instance.getHelper().print(MainExecutor.getRedeemedMessage()));
            }
        });
    }

    public void redeem(Player cs, CodeInstance code){
        for (String reward: code.getRewards()) {
            instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), instance.getHelper().replaceTagToPlayer(cs, reward));
        }
        cs.sendMessage(instance.getHelper().print(code.getClaimMessage()));
    }
    public String getName() {
        return "claim";
    }

    public String getPermission() {
        return null;
    }
}
