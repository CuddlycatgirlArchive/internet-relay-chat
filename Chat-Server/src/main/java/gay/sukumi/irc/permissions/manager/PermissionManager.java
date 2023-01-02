package gay.sukumi.irc.permissions.manager;

import gay.sukumi.irc.config.Configuration;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.permissions.MeowPerms;
import gay.sukumi.irc.profile.UserProfile;

import javax.xml.crypto.Data;
import java.security.acl.Group;

public class PermissionManager {

    public boolean hasPermission(String permission, UserProfile profile) {
        Account account = Database.INSTANCE.getUser(profile.getUsername());
        return account != null
                && (account.getPermissions().contains(permission) || MeowPerms.INSTANCE.getGroupManager().hasGroupPermission(permission, account.getGroup()));
    }

    public void addPermission(String permission, UserProfile profile) {
        Account account = Database.INSTANCE.getUser(profile.getUsername());
        if(account == null || account.getPermissions().contains(permission)) return;
        account.getPermissions().add(permission);
        Database.INSTANCE.editUser(account);
    }


    public void removePermission(String permission, UserProfile profile) {
        Account account = Database.INSTANCE.getUser(profile.getUsername());
        if(account == null) return;
        account.getPermissions().remove(permission);
        Database.INSTANCE.editUser(account);
    }

    public boolean hasPermission(String permission, Account account) {
        return account != null
                && (account.getPermissions().contains(permission) || MeowPerms.INSTANCE.getGroupManager().hasGroupPermission(permission, account.getGroup()));
    }

    public void addPermission(String permission, Account account) {
        account.getPermissions().add(permission);
        Database.INSTANCE.editUser(account);
    }


    public void removePermission(String permission, Account account) {
        account.getPermissions().remove(permission);
        Database.INSTANCE.editUser(account);
    }

}
