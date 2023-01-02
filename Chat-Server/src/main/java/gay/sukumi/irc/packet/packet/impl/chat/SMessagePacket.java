package gay.sukumi.irc.packet.packet.impl.chat;

import gay.sukumi.hydra.shared.protocol.packets.Packet;
import gay.sukumi.hydra.shared.protocol.packets.PacketId;
import gay.sukumi.irc.profile.UserProfile;
import gay.sukumi.irc.utils.Crypter;
import gay.sukumi.irc.utils.EnumChatFormatting;
import io.netty.buffer.ByteBuf;

@PacketId(6)
public class SMessagePacket extends Packet {

    private UserProfile userProfile;
    private String message;
    private Type type;

    public SMessagePacket() {
    }

    public SMessagePacket(final UserProfile profile, final Type type, final String message) {
        this.message = EnumChatFormatting.getTextWithoutFormattingCodes(message);
        this.type = type;
        this.userProfile = profile;
    }

    public SMessagePacket(final String message) {
        this.message = message;
        this.type = Type.RAW;
    }

    @Override
    public void read(ByteBuf byteBuf) {
    }

    @Override
    public void write(ByteBuf byteBuf) {
        writeString(byteBuf, Crypter.encode(message));
        writeString(byteBuf, getType().name());
        if (type != Type.RAW)
            writeUserProfile(byteBuf, userProfile);
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public String getMessage() {
        return message;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        RAW, USER
    }
}
