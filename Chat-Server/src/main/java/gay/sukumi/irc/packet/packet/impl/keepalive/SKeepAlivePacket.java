package gay.sukumi.irc.packet.packet.impl.keepalive;

import gay.sukumi.hydra.shared.protocol.packets.Packet;
import gay.sukumi.hydra.shared.protocol.packets.PacketId;
import io.netty.buffer.ByteBuf;

@PacketId(26)
public class SKeepAlivePacket extends Packet {

    public SKeepAlivePacket() {
    }

    @Override
    public void read(ByteBuf byteBuf) {
    }

    @Override
    public void write(ByteBuf byteBuf) {
    }

}
