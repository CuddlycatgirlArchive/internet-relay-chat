package gay.sukumi.cli.impl;

import gay.sukumi.cli.Command;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.profile.UserProfile;

public class RankCommand extends Command {
    public RankCommand() {
        super(new String[]{"user"}, "Changes a users password", "rank", "promote", "demote", "admin");
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
        if(userProfile.getRank() == UserProfile.Rank.USER) {
            userProfile.setRank(UserProfile.Rank.ADMIN);
        } else {
            userProfile.setRank(UserProfile.Rank.USER);
        }
        Database.INSTANCE.editUser(userProfile);
        ChatServer.LOGGER.info(userProfile.getRank() == UserProfile.Rank.ADMIN ? "Promoted user" : "Demoted user");
    }
}
