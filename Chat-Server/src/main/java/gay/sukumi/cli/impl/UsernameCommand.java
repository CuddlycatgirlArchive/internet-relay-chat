package gay.sukumi.cli.impl;

import gay.sukumi.cli.Command;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;

public class UsernameCommand extends Command {
    public UsernameCommand() {
        super(new String[]{"user", "username"}, "Changes a users name", "username", "user", "changeuser");
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length < 2) {
            sendUsage();
            return;
        }

        Account userProfile = Database.INSTANCE.getUser(args[0]);
        if (userProfile == null) {
            System.out.println(" \033[91mUser not found");
            return;
        }
        userProfile.setPassword(args[1]);
        Database.INSTANCE.editUser(userProfile);
        ChatServer.LOGGER.info("Changed username");
    }
}
