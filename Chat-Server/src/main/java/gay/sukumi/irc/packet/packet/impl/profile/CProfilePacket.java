package gay.sukumi.irc.packet.packet.impl.profile;

import gay.sukumi.hydra.shared.protocol.packets.Packet;
import gay.sukumi.hydra.shared.protocol.packets.PacketId;
import gay.sukumi.irc.profile.UserProfile;
import io.netty.buffer.ByteBuf;

@PacketId(3)
public class CProfilePacket extends Packet {

    private UserProfile profile;
    private Action action;

    public CProfilePacket() {
    }

    public CProfilePacket(Action action, UserProfile profile) {
        this.action = action;
        this.profile = profile;
    }

    @Override
    public void read(ByteBuf byteBuf) {
        action = Action.valueOf(readString(byteBuf));
        profile = readUserProfile(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf) {
        writeString(byteBuf, action.name());
        writeUserProfile(byteBuf, profile);
    }

    public UserProfile getProfile() {
        return profile;
    }

    public Action getAction() {
        return action;
    }

    public enum Action {
        ADD,
        REMOVE,
        CHANGE_NAME,
        CHANGE_SERVER
    }

}
