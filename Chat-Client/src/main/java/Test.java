import de.datasecs.hydra.shared.handler.Session;
import gay.sukumi.irc.ChatClient;
import gay.sukumi.irc.listener.ConnectionListener;
import gay.sukumi.irc.listener.LoginListener;
import gay.sukumi.irc.listener.UserProfileListener;
import gay.sukumi.irc.packet.packet.impl.chat.CMessagePacket;
import gay.sukumi.irc.profile.UserProfile;
import gay.sukumi.irc.utils.Errors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

public class Test {

    public static void main(String[] args) throws InterruptedException {

        ChatClient.INSTANCE.addListener((message, type, userProfile) -> {
            switch (type) {
                case RAW:
                    System.out.println(message);
                    break;
                case USER:
                    System.out.println("[" + userProfile.getUsername() + "] " + message);
                    break;
            }
        });

        ChatClient.INSTANCE.addListener(new LoginListener() {
            @Override
            public void onSuccess(UserProfile userProfile, Session session) {
                System.out.println("Successfully logged in!");
            }

            @Override
            public void onFail(Errors reason, Session session) {
                System.out.println("Failed to login! -> " + reason.name());
                return;
            }
        });

        ChatClient.INSTANCE.addListener(new UserProfileListener() {

            @Override
            public void onConnected(UserProfile profile) {
                System.out.println(profile.getUsername() + " connected!");
            }

            @Override
            public void onDisconnected(UserProfile profile) {
                System.out.println(profile.getUsername() + " disconnected!");
            }

            @Override
            public void onNameChange(UserProfile newProfile, UserProfile oldProfile) {
                System.out.println(newProfile.getUsername() + " changed their mc name to '" + newProfile.getMcUsername() + "'!");
            }

            @Override
            public void onServerChange(UserProfile newProfile, UserProfile oldProfile) {
                System.out.println("Changed their server");
            }

        });


        /* Connect to the IRC */
        ChatClient.INSTANCE.connect(new InetSocketAddress("irc.sukumi.gay", 8888),
                "lucy",
                "meow123",
                "awdaw",
                UserProfile.Client.SUKUMI);

        /* Add shutdown hook */
        Runtime.getRuntime().addShutdownHook(new Thread(ChatClient.INSTANCE::disconnect));

        while (true) {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));

                String name = reader.readLine();
                ChatClient.INSTANCE.sendPacket(new CMessagePacket(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
