package gay.sukumi.irc.packet.packet.impl.chat;

import gay.sukumi.hydra.shared.protocol.packets.Packet;
import gay.sukumi.hydra.shared.protocol.packets.PacketId;
import gay.sukumi.irc.utils.Crypter;
import io.netty.buffer.ByteBuf;

@PacketId(5)
public class CMessagePacket extends Packet {

    private String uuid;
    private String message;

    public CMessagePacket() {}

    public CMessagePacket(final String message) {
        this.message = message;
    }

    @Override
    public void read(ByteBuf byteBuf) {
        uuid = readString(byteBuf);
        message = Crypter.decode(readString(byteBuf));
    }

    @Override
    public void write(ByteBuf byteBuf) {

    }

    public String getUuid() {
        return uuid;
    }

    public String getMessage() {
        return message;
    }
}
