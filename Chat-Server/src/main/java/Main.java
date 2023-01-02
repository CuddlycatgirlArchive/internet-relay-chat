import gay.sukumi.cli.CommandRegistry;
import gay.sukumi.irc.ChatServer;
import gay.sukumi.irc.config.Configuration;
import gay.sukumi.irc.database.Database;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /* Load config */
        Configuration.INSTANCE.load();

        /* Register commands */
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.init();

        /* Connect to database */
        Database.INSTANCE.connect();

        /* Bind socket */
        ChatServer.INSTANCE.bind(new InetSocketAddress(Configuration.INSTANCE.get().getPort()));

        /* CLI commands */
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String lol = scanner.nextLine();
            commandRegistry.runCommand(lol);
        }
    }
}
