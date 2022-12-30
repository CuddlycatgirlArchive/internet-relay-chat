package gay.sukumi.cli.impl;

import gay.sukumi.cli.Command;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;

public class UnmuteCommand extends Command {
    public UnmuteCommand() {
        super(new String[]{"user"}, "Unmutes an user.", "unmute");
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length < 1) {
            sendUsage();
            return;
        }

        Account userProfile = Database.INSTANCE.getUser(args[0]);
        if (userProfile == null) {
            ChatServer.LOGGER.error("User not found");
            return;
        }
        userProfile.setMuted(false);
        Database.INSTANCE.editUser(userProfile);
        ChatServer.INSTANCE.broadcastPacket(new SMessagePacket("Unmuted " + userProfile.username));
    }
}
