package gay.sukumi.cli.impl;

import gay.sukumi.cli.Command;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;

public class BroadcastCommand extends Command {
    public BroadcastCommand() {
        super(new String[]{"message"}, "Broadcast a message", "broadcast", "bc");
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length < 1) {
            sendUsage();
            return;
        }
        ChatServer.INSTANCE.broadcastPacket(new SMessagePacket(String.join(" ", args)));
    }
}
