package gay.sukumi.irc.command.impl;

import gay.sukumi.hydra.shared.handler.Session;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.command.Command;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.profile.UserProfile;
import gay.sukumi.irc.utils.EnumChatFormatting;

public class RemoveUserCommand extends Command {
    public RemoveUserCommand() {
        super(new String[]{"user"}, "Removes a user from the database", "rmuser", "removeuser");
    }

    @Override
    public void onExecute(Session session, UserProfile profile, String[] args) {
        if (args.length < 1) {
            sendUsage(session);
            return;
        }

        if (profile.getRank() != UserProfile.Rank.ADMIN && !profile.getUsername().equalsIgnoreCase("Lucy")) {
            session.send(new SMessagePacket(EnumChatFormatting.RED + "You need the " + EnumChatFormatting.DARK_AQUA + "Super Administrator" + EnumChatFormatting.RED + " rank for this command."));
            return;
        }

        Account userProfile = Database.INSTANCE.getUser(args[0]);
        if (userProfile == null) {
            session.send(new SMessagePacket(EnumChatFormatting.RED + "User doesn't exist."));
            return;
        }

        Database.INSTANCE.removeUser(userProfile);
        session.send(new SMessagePacket(EnumChatFormatting.GREEN + "Successfully removed " + EnumChatFormatting.DARK_AQUA + userProfile.getUsername() + EnumChatFormatting.GREEN + "."));
    }
}
