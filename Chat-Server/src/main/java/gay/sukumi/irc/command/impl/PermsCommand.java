package gay.sukumi.irc.command.impl;

import gay.sukumi.hydra.shared.handler.Session;
import gay.sukumi.irc.command.Command;
import gay.sukumi.irc.config.Configuration;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.permissions.MeowPerms;
import gay.sukumi.irc.profile.UserProfile;
import gay.sukumi.irc.utils.EnumChatFormatting;

public class PermsCommand extends Command {
    public PermsCommand() {
        super(new String[]{"user", "add/remove", "permission"}, "irc.admin.perms", "Changes permissions", "perms", "permissions", "meowperms", "perm");
    }

    @Override
    public void onExecute(Session session, UserProfile profile, String[] args) {
        if (args.length < 1) {
            session.send(new SMessagePacket(EnumChatFormatting.GREEN + "This IRC is running " + EnumChatFormatting.LIGHT_PURPLE + "Lucy's Permission System" + EnumChatFormatting.GREEN + "."));
            session.send(new SMessagePacket(EnumChatFormatting.GREEN + "> " + EnumChatFormatting.WHITE + "/perms <user> add <permission>"));
            session.send(new SMessagePacket(EnumChatFormatting.GREEN + "> " + EnumChatFormatting.WHITE + "/perms <user> rm <permission>"));
            session.send(new SMessagePacket(EnumChatFormatting.GREEN + "> " + EnumChatFormatting.WHITE + "/perms <user> setgroup <group>"));
            session.send(new SMessagePacket(EnumChatFormatting.GREEN + "> " + EnumChatFormatting.WHITE + "/perms <user> rmgroup <group>"));
            session.send(new SMessagePacket(EnumChatFormatting.GREEN + "> " + EnumChatFormatting.WHITE + "/perms <user> lookup"));
            session.send(new SMessagePacket(EnumChatFormatting.GREEN + "> " + EnumChatFormatting.WHITE + "/perms listgroups"));
            return;
        }


        if (args.length >= 2) {
            Account account = Database.INSTANCE.getUser(args[0]);
            if (account == null) {
                session.send(new SMessagePacket(EnumChatFormatting.DARK_AQUA + args[0] + EnumChatFormatting.RED + " could not be found in the database."));
                return;
            }
            switch (args[1]) {
                case "add":
                    if (args.length < 3) {
                        session.send(new SMessagePacket(EnumChatFormatting.RED + "Usage: " + EnumChatFormatting.WHITE + "/perms <user> add <permission>"));
                        return;
                    }
                    MeowPerms.INSTANCE.getPermissionManager().addPermission(args[2], account);
                    session.send(new SMessagePacket(EnumChatFormatting.GREEN + String.format("Added permission %s%s %sto user %s%s", EnumChatFormatting.DARK_AQUA, args[2], EnumChatFormatting.GREEN, EnumChatFormatting.DARK_AQUA, profile.getUsername())));
                    break;
                case "rm":
                    if (args.length < 3) {
                        session.send(new SMessagePacket(EnumChatFormatting.RED + "Usage: " + EnumChatFormatting.WHITE + "/perms <user> add <permission>"));
                        return;
                    }
                    MeowPerms.INSTANCE.getPermissionManager().removePermission(args[2], account);
                    session.send(new SMessagePacket(EnumChatFormatting.GREEN + String.format("Removed permission %s%s %sfrom user %s%s", EnumChatFormatting.DARK_AQUA, args[2], EnumChatFormatting.GREEN, EnumChatFormatting.DARK_AQUA, profile.getUsername())));
                    break;
                case "rmgroup":
                    if (args.length < 3) {
                        session.send(new SMessagePacket(EnumChatFormatting.RED + "Usage: " + EnumChatFormatting.WHITE + "/perms <user> rmgroup <group>"));
                        return;
                    }
                    MeowPerms.INSTANCE.getGroupManager().setGroup("user", account);
                    session.send(new SMessagePacket(EnumChatFormatting.GREEN + String.format("Removed group from user %s%s%s.", EnumChatFormatting.DARK_AQUA, profile.getUsername(), EnumChatFormatting.GREEN)));
                    break;
                case "setgroup":
                    if (args.length < 3) {
                        session.send(new SMessagePacket(EnumChatFormatting.RED + "Usage: " + EnumChatFormatting.WHITE + "/perms <user> setgroup <group>"));
                        return;
                    }
                    MeowPerms.INSTANCE.getGroupManager().setGroup(args[2], account);
                    session.send(new SMessagePacket(EnumChatFormatting.GREEN + String.format("Added group %s%s %sto user %s%s", EnumChatFormatting.DARK_AQUA, args[2], EnumChatFormatting.GREEN, EnumChatFormatting.DARK_AQUA, profile.getUsername())));
                    break;
                case "lookup":
                    session.send(new SMessagePacket(EnumChatFormatting.DARK_AQUA + account.getUsername() + " " + EnumChatFormatting.GREEN + "has " + (account.getPermissions().size() + MeowPerms.INSTANCE.getGroupManager().groupPermissionSize(account.getGroup())) + " permissions and is in group " + EnumChatFormatting.DARK_AQUA + account.getGroup() + EnumChatFormatting.GREEN + "."));
                    session.send(new SMessagePacket(EnumChatFormatting.GREEN + "User permissions:"));
                    account.getPermissions().forEach(permission -> {
                        session.send(new SMessagePacket(EnumChatFormatting.GREEN + "> " + EnumChatFormatting.WHITE + permission));
                    });
                    if(MeowPerms.INSTANCE.getGroupManager().groupPermissionSize(account.getGroup()) > 0) {
                        session.send(new SMessagePacket(EnumChatFormatting.GREEN + "Group permissions:"));
                        MeowPerms.INSTANCE.getGroupManager().groupPermissions(account.getGroup()).forEach(permission -> {
                            session.send(new SMessagePacket(EnumChatFormatting.GREEN + "> " + EnumChatFormatting.WHITE + permission));
                        });
                    }
                    break;
            }
        } else {
            session.send(new SMessagePacket(EnumChatFormatting.GREEN + "Groups:"));
            Configuration.INSTANCE.get().getGroups().forEach((k, v) -> {
                session.send(new SMessagePacket(EnumChatFormatting.GREEN + "> " + EnumChatFormatting.WHITE + k + ": " + v.size()));
            });
        }
        super.onExecute(session, profile, args);
    }
}
