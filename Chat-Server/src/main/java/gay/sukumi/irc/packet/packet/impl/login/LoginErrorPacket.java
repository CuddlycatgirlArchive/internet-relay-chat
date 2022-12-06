package gay.sukumi.irc.packet.packet.impl.login;

import de.datasecs.hydra.shared.protocol.packets.Packet;
import de.datasecs.hydra.shared.protocol.packets.PacketId;
import gay.sukumi.irc.utils.Errors;
import io.netty.buffer.ByteBuf;

@PacketId(2)
public class LoginErrorPacket extends Packet {

    private Errors reason;

    public LoginErrorPacket(Errors reason) {
        this.reason = reason;
    }

    @Override
    public void read(ByteBuf byteBuf) {
        reason = Errors.valueOf(readString(byteBuf));
    }

    @Override
    public void write(ByteBuf byteBuf) {
        writeString(byteBuf, getReason().name());
    }

    public Errors getReason() {
        return reason;
    }
}
