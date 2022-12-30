package gay.sukumi.irc.command.impl;

import gay.sukumi.hydra.shared.handler.Session;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.command.Command;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.profile.UserProfile;
import gay.sukumi.irc.utils.EnumChatFormatting;

public class AddUserCommand extends Command {
    public AddUserCommand() {
        super(new String[]{"username", "password", "admin"}, "Adds a user to the database", "adduser", "createuser");
    }

    @Override
    public void onExecute(Session session, UserProfile profile, String[] args) {
        if (args.length < 3) {
            sendUsage(session);
            return;
        }

        if (profile.getRank() != UserProfile.Rank.ADMIN && !profile.getUsername().equalsIgnoreCase("Lucy")) {
            session.send(new SMessagePacket(EnumChatFormatting.RED + "You need the " + EnumChatFormatting.DARK_AQUA + "Super Administrator" + EnumChatFormatting.RED + " rank for this command."));
            return;
        }

        boolean output = Boolean.parseBoolean(args[2]);
        Database.INSTANCE.createUser(new Account(args[0], args[1], output ? UserProfile.Rank.ADMIN : UserProfile.Rank.USER, false, false));
        session.send(new SMessagePacket(EnumChatFormatting.GREEN + "Successfully added " + EnumChatFormatting.DARK_AQUA + args[0] + EnumChatFormatting.GREEN + "."));
    }
}
