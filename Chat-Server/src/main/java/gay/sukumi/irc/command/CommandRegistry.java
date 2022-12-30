package gay.sukumi.irc.command;


import gay.sukumi.hydra.shared.handler.Session;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.command.impl.*;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.profile.UserProfile;

import java.util.*;

public class CommandRegistry {

    final List<Command> commands = new LinkedList<>();

    public void init() {
        addCommand(new UnmuteCommand());
        addCommand(new MuteCommand());
        addCommand(new RemoveUserCommand());
        addCommand(new RankCommand());
        addCommand(new PasswordCommand());
        addCommand(new AddUserCommand());
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

    public final void runCommand(final Session session, final UserProfile profile, final String message) {
        final String[] s = message.split("( )+");
        final String c = s[0].substring("/".length());
        final String[] a = Arrays.copyOfRange(s, 1, s.length);
        final Command command = this.getCommand(c);
        if (command == null) {
            session.send(new SMessagePacket(null, SMessagePacket.Type.RAW, "Invalid command."));
            return;
        }

        command.onExecute(session, profile, a);
    }

}
