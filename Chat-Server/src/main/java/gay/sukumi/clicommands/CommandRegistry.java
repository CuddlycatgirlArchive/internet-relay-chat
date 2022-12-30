package gay.sukumi.clicommands;


import gay.sukumi.clicommands.impl.*;
import gay.sukumi.irc.ChatServer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommandRegistry {

    final List<Command> commands = new LinkedList<>();

    public void init() {
        addCommand(new UnmuteCommand());
        addCommand(new MuteCommand());
        addCommand(new BroadcastCommand());
        addCommand(new SendAsCommand());
        addCommand(new AddUserCommand());
        addCommand(new RebootCommand());
    }

    private void addCommand(Command cmd) {
        if (this.commands.contains(cmd)) return;
        this.commands.add(cmd);
    }

    public final List<Command> getCommands() {
        return this.commands;
    }

    public final <T extends Command> T getCommand(final Class<T> clazz) {
        return (T) this.commands.stream().filter(cmd -> cmd.getClass() == clazz).findFirst().orElse(null);
    }

    public final <T extends Command> T getCommand(final String command) {
        return (T) this.commands.stream().filter(cmd -> Arrays.asList(cmd.getAliases()).contains(command)).findFirst().orElse(null);
    }

    public final void runCommand(final String message) {
        final String[] s = message.split("( )+");
        final String c = s[0];
        final String[] a = Arrays.copyOfRange(s, 1, s.length);
        final Command command = this.getCommand(c);
        if (command == null) {
            ChatServer.LOGGER.error("Invalid command.");
            return;
        }

        command.onExecute(a);
    }

}
