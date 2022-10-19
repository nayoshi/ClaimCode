package dev.nayo.claimcode;

import java.util.ArrayList;
import java.util.List;

public class CodeInstance {
    private ClaimCode instance;
    private List<String> keys;
    private List<String> rewards;
    private List<String> blacklists;
    private boolean enabled;
    private String claimMessage;
    private String name;
    public CodeInstance() {
        instance = ClaimCode.getInstance();
        keys = new ArrayList<>();
        rewards = new ArrayList<>();
        blacklists = new ArrayList<>();
        enabled = false;
        claimMessage = "";
        name = "";
    }


    public CodeInstance setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public CodeInstance setBlacklists(List<String> blacklists) {
        this.blacklists = blacklists;
        return this;
    }

    public CodeInstance setKeys(List<String> keys) {
        this.keys = keys;
        return this;
    }

    public CodeInstance setRewards(List<String> rewards) {
        this.rewards = rewards;
        return this;
    }

    public List<String> getKeys() {
        return keys;
    }
    public List<String> getRewards() {
        return rewards;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public List<String> getBlacklists() {
        return blacklists;
    }

    public CodeInstance setClaimMessage(String claimMessage) {
        this.claimMessage = claimMessage;
        return this;
    }
    public CodeInstance setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
    public String getClaimMessage() {
        return claimMessage;
    }
}
