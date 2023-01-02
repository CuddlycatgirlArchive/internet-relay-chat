package gay.sukumi.hydra.shared.initializer;

import gay.sukumi.hydra.shared.handler.HydraSession;
import gay.sukumi.hydra.shared.protocol.HydraProtocol;
import gay.sukumi.hydra.shared.protocol.packets.serialization.PacketDecoder;
import gay.sukumi.hydra.shared.protocol.packets.serialization.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Created with love by DataSecs on 29.09.2017.
 */
public class HydraChannelInitializer extends ChannelInitializer<SocketChannel> {

    private HydraProtocol protocol;

    private boolean isServer;

    public HydraChannelInitializer(HydraProtocol protocol, boolean isServer) {
        this.protocol = protocol;
        this.isServer = isServer;
    }

    @Override
    protected void initChannel(SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();

        // In
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
        pipeline.addLast(new PacketDecoder(protocol));

        // Out
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new PacketEncoder(protocol));

        HydraSession session = new HydraSession(channel, protocol);
        pipeline.addLast(session);

        // Add sessions to protocol, to keep track of them
        if (isServer) {
            protocol.addSession(session);
        } else {
            protocol.setClientSession(session);
        }

        if (protocol.getSessionListener() != null) {
            // Inform SessionListener about new session
            protocol.callSessionListener(true, session);
        }
    }
}