package gay.sukumi.irc.permissions;

import gay.sukumi.irc.permissions.manager.GroupManager;
import gay.sukumi.irc.permissions.manager.PermissionManager;

public class MeowPerms {

    public static final MeowPerms INSTANCE = new MeowPerms();
    private final PermissionManager permissionManager = new PermissionManager();
    private final GroupManager groupManager = new GroupManager();

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }
}
