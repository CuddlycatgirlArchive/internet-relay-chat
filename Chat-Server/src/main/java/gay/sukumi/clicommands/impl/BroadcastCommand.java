package gay.sukumi.clicommands.impl;

import gay.sukumi.clicommands.Command;
import gay.sukumi.database.Account;
import gay.sukumi.database.Database;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;

public class BroadcastCommand extends Command {
    public BroadcastCommand() {
        super(new String[]{"message"}, "Broadcast.", "broadcast", "bc");
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
