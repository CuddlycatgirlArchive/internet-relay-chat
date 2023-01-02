package gay.sukumi.irc.command.impl;

import gay.sukumi.hydra.shared.handler.Session;
import gay.sukumi.irc.command.Command;
import gay.sukumi.irc.config.Configuration;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.profile.UserProfile;
import gay.sukumi.irc.utils.EnumChatFormatting;

public class PasswordCommand extends Command {
    public PasswordCommand() {
        super(new String[]{"user", "password"}, "irc.admin.passwd", "Changes a users password", "pwd", "passwd", "changepw");
    }

    @Override
    public void onExecute(Session session, UserProfile profile, String[] args) {
        if (args.length < 2) {
            sendUsage(session);
            return;
        }

        Account userProfile = Database.INSTANCE.getUser(args[0]);
        if (userProfile == null) {
            session.send(new SMessagePacket(EnumChatFormatting.DARK_AQUA + args[0] + EnumChatFormatting.RED + " could not be found in the database."));
            return;
        }

        userProfile.setPassword(args[1]);

        Database.INSTANCE.editUser(userProfile);

        session.send(new SMessagePacket(EnumChatFormatting.GREEN + "Changed " + EnumChatFormatting.DARK_AQUA + userProfile.getUsername() + EnumChatFormatting.GREEN + "'s password successfully."));
    }
}


