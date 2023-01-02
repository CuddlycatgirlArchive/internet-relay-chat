package gay.sukumi.irc.command;

import gay.sukumi.hydra.shared.handler.Session;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.profile.UserProfile;
import gay.sukumi.irc.utils.EnumChatFormatting;

public class Command {

    final String[] aliases;
    final String[] usageParameter;
    final String permission;
    final String description;

    public Command(final String[] usageParameter, final String permission, final String description, final String... aliases) {
        if (aliases.length == 0) this.aliases = new String[]{this.getClass().getSimpleName().replace("Command", "")};
        else this.aliases = aliases;
        this.usageParameter = usageParameter;
        this.permission = permission;
        this.description = description;
    }

    public final String[] getAliases() {
        return this.aliases;
    }

    public final String[] getUsageParameter() {
        return this.usageParameter;
    }

    public final String getDescription() {
        return this.description;
    }


    public String getPermission() {
        return permission;
    }

    public void onExecute(Session session, UserProfile profile, final String[] args) {
    }

    public void sendUsage(Session session) {
        StringBuilder usages = new StringBuilder();
        for (final String usage : this.getUsageParameter())
            usages.append(" <").append(usage).append(">");
        session.send(new SMessagePacket(String.format("%sUsage: %s/%s%s.", EnumChatFormatting.RED, EnumChatFormatting.WHITE, getAliases()[0] + usages, EnumChatFormatting.RED)));
    }

    public String getAliasesAsString() {
        StringBuilder alias = new StringBuilder();
        for (final String aliases : this.getAliases())
            alias.append(getAliases()[getAliases().length - 1].equalsIgnoreCase(aliases) ? " " + aliases + " " : " " + aliases + ", ");
        return alias.toString();
    }

}