package gay.sukumi.irc.command.impl;

import gay.sukumi.hydra.shared.handler.Session;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.command.Command;
import gay.sukumi.irc.database.Account;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.profile.UserProfile;
import gay.sukumi.irc.utils.EnumChatFormatting;

public class MuteCommand extends Command {
    public MuteCommand() {
        super(new String[]{"user"}, "irc.admin.mute", "Mutes an user.", "mute");
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

        /* Set the muted status to false */
        account.setMuted(false);

        /* Modify the users account in the database */
        Database.INSTANCE.editUser(account);

        session.send(new SMessagePacket(EnumChatFormatting.GREEN + "Successfully muted " + EnumChatFormatting.DARK_AQUA + args[0] + EnumChatFormatting.GREEN + "."));
        ChatServer.INSTANCE.broadcastPacket(new SMessagePacket(profile.getUsername() + " muted " + account.getUsername()));
        super.onExecute(session, profile, args);
    }
}
