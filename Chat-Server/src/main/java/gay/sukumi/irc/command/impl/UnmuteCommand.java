package gay.sukumi.irc.command.impl;

import gay.sukumi.hydra.shared.handler.Session;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.command.Command;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.profile.UserProfile;

public class UnmuteCommand extends Command {
    public UnmuteCommand() {
        super(new String[]{}, "Unmutes an user.", "unmute");
    }

    @Override
    public void onExecute(Session session, UserProfile profile, String[] args) {

        if (profile.getRank() != UserProfile.Rank.ADMIN) {
            session.send(new SMessagePacket("" + getAliases()[0] + ": Permission denied"));
            return;
        }

        if (args.length < 1) {
            session.send(new SMessagePacket("Usage: /unmute <user>"));
            return;
        }

        Account userProfile = Database.INSTANCE.getUser(args[0]);
        if (userProfile == null) {
            session.send(new SMessagePacket("" + args[0] + ": User not found"));
            return;
        }
        userProfile.setMuted(false);
        Database.INSTANCE.editUser(userProfile);
        ChatServer.INSTANCE.broadcastPacket(new SMessagePacket(profile.getUsername() + " unmuted " + userProfile.username));
        super.onExecute(session, profile, args);
    }
}
