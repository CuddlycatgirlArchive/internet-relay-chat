package gay.sukumi.cli.impl;

import gay.sukumi.cli.Command;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;

public class RemoveUserCommand extends Command {
    public RemoveUserCommand() {
        super(new String[]{"user"}, "Removes a user from the database", "rmuser", "removeuser");
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
        Database.INSTANCE.removeUser(userProfile);
        ChatServer.LOGGER.info("Removed user");
    }
}
