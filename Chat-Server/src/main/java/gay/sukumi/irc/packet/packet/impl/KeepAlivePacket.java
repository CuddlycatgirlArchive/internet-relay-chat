package gay.sukumi.irc.packet.packet.impl;

import de.datasecs.hydra.shared.protocol.packets.Packet;
import de.datasecs.hydra.shared.protocol.packets.PacketId;
import io.netty.buffer.ByteBuf;

@PacketId(Byte.MAX_VALUE)
public class KeepAlivePacket extends Packet {

    public KeepAlivePacket() {}

    @Override
    public void read(ByteBuf byteBuf) {}

    @Override
    public void write(ByteBuf byteBuf) {
    }

}
