package dev.nayo.claimcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class CodeManager {
    private ClaimCode instance;
    private HashMap<String, CodeInstance> keyToCode;
    private List<CodeInstance> codeInstanceList;
    private HashMap<String, CodeInstance> stringToInstance;
    public CodeManager(){
        instance = ClaimCode.getInstance();
        keyToCode = new HashMap<>();
        stringToInstance = new HashMap<>();
        Set<String> codeSet = instance.getConfig().getConfigurationSection("codes").getKeys(false);
        codeInstanceList = new ArrayList<>();

        for (String codeString: codeSet) {
            if (instance.isDisabled()) {
                return;
            }
            CodeInstance code = new CodeInstance()
                    .setName(codeString)
                    .setClaimMessage(instance.getConfig().getString(formatter(codeString, "claim-msg")))
                    .setBlacklists(instance.getConfig().getStringList(formatter(codeString, "blacklists")))
                    .setKeys(instance.getConfig().getStringList(formatter(codeString, "keys")))
                    .setRewards(instance.getConfig().getStringList(formatter(codeString, "rewards")))
                    .setEnabled(instance.getConfig().getBoolean(formatter(codeString, "enabled")));
            for(String key: code.getKeys()) {
                if (!keyToCode.containsKey(key)) {
                    keyToCode.put(key, code);
                } else {
                    instance.setDisabled(true);
                    for(int i = 0; i < 5; i++) {
                        instance.getLogger().log(Level.SEVERE, "ClaimCode has been disabled due to a malformed configuration" +
                                " [keys has been repeated. Keys has to be unique even in their own category]");
                    }
                }
            }
            stringToInstance.put(code.getName(), code);
            codeInstanceList.add(code);
        }
    }
    private String formatter(String name, String append) {
        return "codes." + name + "." + append;
    }
}
