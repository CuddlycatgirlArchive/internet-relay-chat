package gay.sukumi.clicommands.impl;

import gay.sukumi.clicommands.Command;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.profile.UserProfile;

public class AddUserCommand extends Command {
    public AddUserCommand() {
        super(new String[]{"username", "password", "admin"}, "Adds an user.", "adduser", "createuser");
    }

    @Override
    public void onExecute(String[] args) {
        if (args.length < 3) {
            sendUsage();
            return;
        }
        ChatServer.LOGGER.info("Successfully created user!");
        boolean output = Boolean.parseBoolean(args[2]);
        Database.INSTANCE.createUser(new Account(args[0], args[1], output ? UserProfile.Rank.ADMIN : UserProfile.Rank.USER, false, false));
    }
}
