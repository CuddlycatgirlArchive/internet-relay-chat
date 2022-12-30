package gay.sukumi.irc.packet.packet.impl.login;

import gay.sukumi.hydra.shared.protocol.packets.Packet;
import gay.sukumi.hydra.shared.protocol.packets.PacketId;
import gay.sukumi.irc.profile.UserProfile;
import io.netty.buffer.ByteBuf;

@PacketId(1)
public class LoginSuccessPacket extends Packet {

    private UserProfile userProfile;

    public LoginSuccessPacket() {
    }

    public LoginSuccessPacket(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public void read(ByteBuf byteBuf) {
    }

    @Override
    public void write(ByteBuf byteBuf) {
        writeUserProfile(byteBuf, getUserProfile());
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

}
