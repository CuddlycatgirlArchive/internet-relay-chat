package gay.sukumi.irc.permissions.manager;

import gay.sukumi.irc.config.Configuration;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.profile.UserProfile;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class GroupManager {

    public void setGroup(String group, UserProfile profile) {
        Account account = Database.INSTANCE.getUser(profile.getUsername());
        if(account == null || account.getGroup().equalsIgnoreCase(group)) return;
        account.setGroup(group);
        Database.INSTANCE.editUser(account);
    }

    public void setGroup(String group, Account account) {
        if(account.getGroup().equalsIgnoreCase(group)) return;
        account.setGroup(group);
        Database.INSTANCE.editUser(account);
    }

    public boolean isInGroup(String group, UserProfile profile) {
        Account account = Database.INSTANCE.getUser(profile.getUsername());
        if(account == null || !account.getGroup().equalsIgnoreCase(group)) return false;
        return account.getGroup().equalsIgnoreCase(group);
    }

    public int groupPermissionSize(String group) {
        if(Configuration.INSTANCE.get().getGroups().get(group) == null) return 0;
        return Configuration.INSTANCE.get().getGroups().get(group).size();
    }

    public List<String> groupPermissions(String group) {
        if(Configuration.INSTANCE.get().getGroups().get(group) == null) return new ArrayList<>();
        return Configuration.INSTANCE.get().getGroups().get(group);
    }

    public boolean hasGroupPermission(String permission, String group) {
        if(Configuration.INSTANCE.get().getGroups().get(group) == null) return false;
        return Configuration.INSTANCE.get().getGroups().get(group).contains(permission);
    }

}
