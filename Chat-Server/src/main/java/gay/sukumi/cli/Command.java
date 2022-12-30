package gay.sukumi.cli;

import gay.sukumi.irc.ChatServer;

public class Command {

    final String[] aliases;
    final String[] usageParameter;
    final String description;

    public Command(final String[] usageParameter, final String description, final String... aliases) {
        if (aliases.length == 0) this.aliases = new String[]{this.getClass().getSimpleName().replace("Command", "")};
        else this.aliases = aliases;
        this.usageParameter = usageParameter;
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

    public void onExecute(final String[] args) {
    }

    public void sendUsage() {
        StringBuilder usages = new StringBuilder();
        for (final String usage : this.getUsageParameter())
            usages.append(" <").append(usage).append(">");
        ChatServer.LOGGER.error("Usage: " + getAliases()[0] + usages + "\033[0m");
    }

    public String getAliasesAsString() {
        StringBuilder alias = new StringBuilder();
        for (final String aliases : this.getAliases())
            alias.append(getAliases()[getAliases().length - 1].equalsIgnoreCase(aliases) ? " " + aliases + " " : " " + aliases + ", ");
        return alias.toString();
    }

}