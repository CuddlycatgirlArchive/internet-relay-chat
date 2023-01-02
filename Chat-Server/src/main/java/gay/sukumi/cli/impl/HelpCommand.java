package gay.sukumi.cli.impl;

import gay.sukumi.cli.Command;
import gay.sukumi.irc.ChatServer;

public class HelpCommand extends Command {
    public HelpCommand() {
        super(new String[]{""}, "Lists all commands.", "help", "?", "commands");
    }

    @Override
    public void onExecute(String[] args) {
        ChatServer.INSTANCE.getCommandRegistry().getCommands().forEach(command -> {
            System.out.println(" | " + command.getAliasesAsString() + command.getAliasesAsString());
        });
    }
}
