package gay.sukumi.irc.packet.packet.impl.keepalive;

import de.datasecs.hydra.shared.protocol.packets.Packet;
import de.datasecs.hydra.shared.protocol.packets.PacketId;
import io.netty.buffer.ByteBuf;

@PacketId(26)
public class SKeepAlivePacket extends Packet {

    public SKeepAlivePacket() {}

    @Override
    public void read(ByteBuf byteBuf) {}

    @Override
    public void write(ByteBuf byteBuf) {
    }

}
