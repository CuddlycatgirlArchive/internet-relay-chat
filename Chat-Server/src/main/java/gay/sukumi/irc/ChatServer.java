package gay.sukumi.irc;

import de.datasecs.hydra.server.HydraServer;
import de.datasecs.hydra.server.Server;
import de.datasecs.hydra.shared.handler.Session;
import de.datasecs.hydra.shared.handler.listener.HydraSessionListener;
import de.datasecs.hydra.shared.protocol.packets.Packet;
import gay.sukumi.database.Account;
import gay.sukumi.database.Database;
import gay.sukumi.irc.command.CommandRegistry;
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket;
import gay.sukumi.irc.packet.packet.impl.profile.SProfilePacket;
import gay.sukumi.irc.packet.protocol.Protocol;
import gay.sukumi.irc.profile.UserProfile;
import io.netty.channel.ChannelOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.HashMap;

/**
 * The main class of the internet relay chat server.
 *
 * @author kittyuwu
 **/
@SuppressWarnings("unused")
public class ChatServer {

    /* ~~ Chat server instance & logger ~~ */
    public static final ChatServer INSTANCE = new ChatServer();

    public static final Logger LOGGER = LogManager.getLogger("Server");
    public static final Logger MESSAGE_LOGGER = LogManager.getLogger("Message");
    public static final Logger USERPROFILE_LOGGER = LogManager.getLogger("UserProfile");
    public static final Logger COMMAND_LOGGER = LogManager.getLogger("Command");

    private final CommandRegistry commandRegistry = new CommandRegistry();

    /* ~~ Hydra server ~~ */
    private HydraServer hServer;

    private final HashMap<Session, UserProfile> profileHashMap = new HashMap<>();

    /**
     * Bind the chat server.
     * @param socketAddress The ip address & port of the internet relay chat
     */
    @SuppressWarnings("all")
    public void bind(InetSocketAddress socketAddress) {

        /* Initialize all commands */
        commandRegistry.init();

        new Thread(() -> {
            while (true) {
                try {
                    profileHashMap.forEach((session, profile) -> {
                        Account account = Database.INSTANCE.getUser(profile.getUsername());
                        if (account == null) {
                            return;
                        }
                        profile.setRank(account.rank);
                    });
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
        }).start();

        /* Connect to the chat server */
        hServer = new Server.Builder(
                socketAddress.getHostString(), socketAddress.getPort(),
                new Protocol())
                .workerThreads(4)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .addListener(new HydraSessionListener() {
                    @Override
                    public void onConnected(Session session) {
                    }

                    @Override
                    public void onDisconnected(Session session) {
                        if (getProfileHashMap().containsKey(session)) {
                            UserProfile userProfile = getProfileHashMap().get(session);
                            broadcastPacket(new SProfilePacket(SProfilePacket.Action.REMOVE, userProfile));
                            getProfileHashMap().remove(session);
                            LOGGER.info(String.format("[%s] User '%s' logged out.", userProfile.getClient().getName(), userProfile.getUsername()));
                        }
                    }
                }).build();

        /* Add shutdown hook */
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));

        ChatServer.LOGGER.info("Listening for connections");
    }

    /**
     * Send a packet to all connected clients.
     *
     * @param packet Packet that will be sent to the clients
     */
    public void broadcastPacket(Packet packet) {
        if(packet instanceof SMessagePacket) {
            SMessagePacket messagePacket = (SMessagePacket) packet;
            switch (messagePacket.getType()) {
                case RAW:
                    MESSAGE_LOGGER.info(String.format("[%s] %s: %s",
                            "None",
                            "System",
                            messagePacket.getMessage()));
                    break;
                case USER:
                    MESSAGE_LOGGER.info(String.format("[%s] %s: %s",
                            messagePacket.getUserProfile().getClient().getName(),
                            messagePacket.getUserProfile().getUsername(),
                            messagePacket.getMessage()));
                    break;
            }
        }
        profileHashMap.forEach((session, userProfile) -> {
            session.send(packet);
        });
    }

    /**
     * Checks if someone is connected to the chat server.
     *
     * @param username the user
     * @return is client connected
     */
    public boolean isConnected(String username) {
        for (UserProfile value : profileHashMap.values()) {
            if (value.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a user profile by their uuid.
     *
     * @param uuid the uuid
     * @return user profile
     */
    public UserProfile getProfileByUUID(String uuid) {
        for (UserProfile value : profileHashMap.values()) {
            if (value.getUuid().equalsIgnoreCase(uuid)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Gets a user profile by their username.
     *
     * @param name the username
     * @return user profile
     */
    public UserProfile getProfileByName(String name) {
        for (UserProfile value : profileHashMap.values()) {
            if (value.getUsername().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Close the chat server.
     */
    public void close() {
        profileHashMap.forEach((session, userProfile) -> session.close());
        hServer.close();
    }

    public HashMap<Session, UserProfile> getProfileHashMap() {
        return profileHashMap;
    }

    public CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }
}