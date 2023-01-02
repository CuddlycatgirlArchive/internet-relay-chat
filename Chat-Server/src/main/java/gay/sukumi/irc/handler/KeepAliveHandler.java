package gay.sukumi.irc.handler;

import gay.sukumi.irc.ChatServer;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class KeepAliveHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        try {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent e = (IdleStateEvent) evt;
                if (e.state() == IdleState.READER_IDLE) {
                    ChatServer.LOGGER.info("Disconnected '" + ChatServer.INSTANCE.getProfileByChannel(ctx.channel()).getUsername() + "' for not sending keep-alive packets");
                    ctx.close();
                }
            }
        } catch (Exception ignored) {
        }
    }
}