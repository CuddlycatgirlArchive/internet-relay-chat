package gay.sukumi.irc.packet.packet.impl.login;

import gay.sukumi.hydra.shared.protocol.packets.Packet;
import gay.sukumi.hydra.shared.protocol.packets.PacketId;
import gay.sukumi.irc.profile.UserProfile;
import io.netty.buffer.ByteBuf;

@PacketId(0)
public class LoginRequestPacket extends Packet {

    private String username, password, mcUsername;
    private UserProfile.Client client;

    /* Empty constructor for Hydra, I don't know why it's like that. */
    public LoginRequestPacket() {}

    @Override
    public void read(ByteBuf byteBuf) {
        username = readString(byteBuf);
        password = readString(byteBuf);
        mcUsername = readString(byteBuf);
        client = UserProfile.Client.valueOf(readString(byteBuf));
    }

    @Override
    public void write(ByteBuf byteBuf) {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMcUsername() {
        return mcUsername;
    }

    public UserProfile.Client getClient() {
        return client;
    }
}
