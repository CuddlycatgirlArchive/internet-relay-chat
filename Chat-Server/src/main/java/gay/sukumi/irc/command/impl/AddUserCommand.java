package gay.sukumi.irc.command.impl;

import gay.sukumi.hydra.shared.handler.Session;
import gay.sukumi.irc.command.Command;
import gay.sukumi.irc.config.Configuration;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.profile.UserProfile;
import gay.sukumi.irc.utils.EnumChatFormatting;

public class AddUserCommand extends Command {
    public AddUserCommand() {
        super(new String[]{"username", "password", "admin"}, "irc.admin.adduser", "Adds a user to the database", "adduser", "createuser");
    }

    @Override
    public void onExecute(Session session, UserProfile profile, String[] args) {
        if (args.length < 3) {
            sendUsage(session);
            return;
        }

        boolean output = Boolean.parseBoolean(args[2]);
        Database.INSTANCE.createUser(new Account(args[0], args[1], output ? UserProfile.Rank.ADMIN : UserProfile.Rank.USER, false, false, "user"));
        session.send(new SMessagePacket(EnumChatFormatting.GREEN + "Successfully added " + EnumChatFormatting.DARK_AQUA + args[0] + EnumChatFormatting.GREEN + "."));
    }
}
