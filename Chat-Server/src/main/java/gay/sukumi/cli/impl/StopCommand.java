package gay.sukumi.cli.impl;

import gay.sukumi.cli.Command;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;

public class StopCommand extends Command {
    public StopCommand() {
        super(new String[]{""}, "Stops the chat server", "stop");
    }

    @Override
    public void onExecute(String[] args) {
        ChatServer.INSTANCE.broadcastPacket(new SMessagePacket("IRC Closed"));
        System.exit(0);

    }
}
