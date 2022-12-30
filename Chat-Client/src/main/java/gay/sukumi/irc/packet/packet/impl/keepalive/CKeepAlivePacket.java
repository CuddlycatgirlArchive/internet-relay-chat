package gay.sukumi.irc.packet.packet.impl.keepalive;

import de.datasecs.hydra.shared.protocol.packets.Packet;
import de.datasecs.hydra.shared.protocol.packets.PacketId;
import io.netty.buffer.ByteBuf;

@PacketId(25)
public class CKeepAlivePacket extends Packet {

    public CKeepAlivePacket() {
    }

    @Override
    public void read(ByteBuf byteBuf) {
    }

    @Override
    public void write(ByteBuf byteBuf) {
    }

}
