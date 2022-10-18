package dev.nayo.claimcode;

import org.bukkit.ChatColor;

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
        String string = instance.getPrefix();
        stringBuilder.append(str);
        return format(stringBuilder.toString());
    }
}
