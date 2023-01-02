package gay.sukumi.irc.packet.packet.impl.login;

import gay.sukumi.hydra.shared.protocol.packets.Packet;
import gay.sukumi.hydra.shared.protocol.packets.PacketId;
import gay.sukumi.irc.utils.Errors;
import io.netty.buffer.ByteBuf;

@PacketId(2)
public class LoginErrorPacket extends Packet {

    private Errors errors;

    @Override
    public void read(ByteBuf byteBuf) {
        errors = Errors.valueOf(readString(byteBuf));
    }

    @Override
    public void write(ByteBuf byteBuf) {

    }

    public Errors getReason() {
        return errors;
    }
}
