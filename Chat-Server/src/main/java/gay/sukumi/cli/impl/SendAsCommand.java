package gay.sukumi.cli.impl;

import gay.sukumi.cli.Command;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.profile.UserProfile;

public class SendAsCommand extends Command {
    public SendAsCommand() {
        super(new String[]{"message"}, "Sends messages as another user", "send", "sendas");
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length < 2) {
            sendUsage();
            return;
        }
        StringBuilder test = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            test.append(args[i]).append(" ");
        }

        UserProfile userProfile = ChatServer.INSTANCE.getProfileByName(args[0]);
        if (userProfile == null) {
            System.out.println(" \033[91mUser not found");
            return;
        }

        ChatServer.INSTANCE.broadcastPacket(new SMessagePacket(userProfile, SMessagePacket.Type.USER, test.toString()));
    }
}
