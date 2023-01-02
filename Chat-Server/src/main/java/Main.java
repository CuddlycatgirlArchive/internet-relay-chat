import gay.sukumi.cli.CommandRegistry;
import gay.sukumi.irc.database.Database;
import gay.sukumi.irc.ChatServer;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.init();
        Database.INSTANCE.connect();
        try {
            ChatServer.INSTANCE.bind(new InetSocketAddress(8888));
        } catch(Exception ignored) {}
        Scanner scanner = new Scanner(System.in);
        System.out.print("\033[0m");
        while (scanner.hasNextLine()) {
            String lol = scanner.nextLine();
            commandRegistry.runCommand(lol);
            System.out.print("\033[0m");
        }
    }
}
