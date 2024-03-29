package gay.sukumi.cli.impl;

import gay.sukumi.cli.Command;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;

public class MuteCommand extends Command {
    public MuteCommand() {
        super(new String[]{"user"}, "Gives the user a message restriction", "mute");
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length < 1) {
            sendUsage();
            return;
        }

        Account userProfile = Database.INSTANCE.getUser(args[0]);
        if (userProfile == null) {
            System.out.println(" \033[91mUser not found");
            return;
        }
        userProfile.setMuted(true);
        Database.INSTANCE.editUser(userProfile);
        ChatServer.INSTANCE.broadcastPacket(new SMessagePacket("Muted " + userProfile.getUsername()));
    }
}
