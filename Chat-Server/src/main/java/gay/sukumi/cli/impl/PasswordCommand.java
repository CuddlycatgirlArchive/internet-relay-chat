package gay.sukumi.cli.impl;

import gay.sukumi.cli.Command;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;

public class PasswordCommand extends Command {
    public PasswordCommand() {
        super(new String[]{"user", "password"}, "Changes a users password", "pwd", "passwd", "changepw");
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
        ChatServer.LOGGER.info("Changed password");
    }
}
