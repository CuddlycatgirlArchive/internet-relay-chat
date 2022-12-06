package gay.sukumi.irc.command.impl;

import de.datasecs.hydra.shared.handler.Session;
import gay.sukumi.database.Account;
import gay.sukumi.database.Database;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.command.Command;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.profile.UserProfile;

public class MuteCommand extends Command {
    public MuteCommand() {
        super(new String[]{}, "Mutes an user.", "mute");
    }

    @Override
    public void onExecute(Session session, UserProfile profile, String[] args) {

        if (profile.getRank() != UserProfile.Rank.ADMIN) {
            session.send(new SMessagePacket(getAliases()[0] + ": Permission denied"));
            return;
        }

        if (args.length < 1) {
            session.send(new SMessagePacket("Usage: /mute <user>"));
            return;
        }

        Account userProfile = Database.INSTANCE.getUser(args[0]);
        if (userProfile == null) {
            session.send(new SMessagePacket(args[0] + ": User not found"));
            return;
        }
        userProfile.setMuted(true);
        Database.INSTANCE.editUser(userProfile);
        ChatServer.INSTANCE.broadcastPacket(new SMessagePacket(profile.getUsername() + " muted " + userProfile.username));
        super.onExecute(session, profile, args);
    }
}
