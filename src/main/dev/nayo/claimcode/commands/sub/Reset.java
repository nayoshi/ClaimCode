package dev.nayo.claimcode.commands.sub;

import dev.nayo.claimcode.ClaimCode;
import dev.nayo.claimcode.commands.MainExecutor;
import dev.nayo.claimcode.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Reset extends SubCommand {
    public ClaimCode instance;

    public Reset() {
        instance = ClaimCode.getInstance();
    }

    public void execute(CommandSender cs, String[] args) {

        if (args.length < 3) {
            instance.getHelper().sendMessage(cs, MainExecutor.getInvalidCommand());
            return;
        }
        Player targetPlayer = instance.getServer().getPlayer(args[1]);

        if (targetPlayer == null) {
            instance.getHelper().sendMessage(cs, "Please select a valid and online player to reset their code redeemed history.");
            return;
        }

        instance.useDatabase().deleteRedeemed(args[2].toLowerCase(), targetPlayer);
        instance.getHelper().sendMessage(cs, "Player: " + targetPlayer.getName() +". Reset claim history for " + args[2].toLowerCase());

    }

    public String getName() {
        return "reset";
    }

    @Override
    public List<String> getArgument(int idx) {
        if (idx == 2){
            List<String> players = new ArrayList<>();
            for(Player player: instance.getServer().getOnlinePlayers()){
                players.add(player.getName());
            }
            return players;
        } else if (idx == 3) {
            List<String> list = new ArrayList<>(instance.getCodeManager().getAllCode());
            list.add("all");
            return list;
        }
        return null;
    }

    public String getPermission() {
        return "claimcode.reset";
    }
}
