package dev.nayo.claimcode;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Helper {
    private ClaimCode instance;
    public Helper() {
        instance = ClaimCode.getInstance();
    }
    public String format(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
    public String print(String str) {
        StringBuilder stringBuilder = new StringBuilder(instance.getPrefix());
        stringBuilder.append(' ');
        stringBuilder.append(str);
        return format(stringBuilder.toString());
    }
    public void sendMessage(CommandSender cs, String str) {
        cs.sendMessage(print(str));
    }
    public String replaceTagToPlayer(CommandSender cs, String str) {
        str = str.replace("{player}", cs.getName());
        str = format(str);
        return str;
    }
}
