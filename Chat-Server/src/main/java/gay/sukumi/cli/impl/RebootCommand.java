package gay.sukumi.cli.impl;

import gay.sukumi.cli.Command;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;

public class RebootCommand extends Command {
    public RebootCommand() {
        super(new String[]{""}, "Restarts the chat server", "reboot");
    }

    @Override
    public void onExecute(String[] args) {
        for (int i = 5; i >= 1; i--) {
            try {
                if (i == 1) {
                    ChatServer.INSTANCE.broadcastPacket(new SMessagePacket("Restarting in " + i + " second"));
                } else {
                    ChatServer.INSTANCE.broadcastPacket(new SMessagePacket("Restarting in " + i + " seconds"));
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.exit(0);

    }
}
