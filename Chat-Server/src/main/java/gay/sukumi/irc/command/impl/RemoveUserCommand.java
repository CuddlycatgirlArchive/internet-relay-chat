package gay.sukumi.irc.command.impl;

import gay.sukumi.hydra.shared.handler.Session;
import gay.sukumi.irc.command.Command;
import gay.sukumi.irc.config.Configuration;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.profile.UserProfile;
import gay.sukumi.irc.utils.EnumChatFormatting;

public class RemoveUserCommand extends Command {
    public RemoveUserCommand() {
        super(new String[]{"user"}, "irc.admin.rmuser", "Removes a user from the database", "rmuser", "removeuser");
    }

    @Override
    public void onExecute(Session session, UserProfile profile, String[] args) {
        if (args.length < 1) {
            sendUsage(session);
            return;
        }

        /* Check if the account exists */
        Account account = Database.INSTANCE.getUser(args[0]);
        if (account == null) {
            session.send(new SMessagePacket(EnumChatFormatting.DARK_AQUA + args[0] + EnumChatFormatting.RED + " could not be found in the database."));
            return;
        }

        Database.INSTANCE.removeUser(account);
        session.send(new SMessagePacket(EnumChatFormatting.GREEN + "Successfully removed " + EnumChatFormatting.DARK_AQUA + account.getUsername() + EnumChatFormatting.GREEN + "."));
    }
}
