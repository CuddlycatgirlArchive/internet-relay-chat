package gay.sukumi.irc.handler;

import gay.sukumi.irc.ChatClient;
import gay.sukumi.irc.packet.packet.impl.keepalive.CKeepAlivePacket;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class KeepAliveHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.WRITER_IDLE) {
                ChatClient.INSTANCE.sendPacket(new CKeepAlivePacket());
            }
        }
    }

}