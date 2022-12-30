package gay.sukumi.irc.packet.protocol

import gay.sukumi.hydra.shared.protocol.HydraProtocol
import gay.sukumi.irc.ChatServer
import gay.sukumi.irc.packet.packet.PacketHandler
import gay.sukumi.irc.packet.packet.impl.keepalive.SKeepAlivePacket
import gay.sukumi.irc.packet.packet.impl.chat.CMessagePacket
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket
import gay.sukumi.irc.packet.packet.impl.keepalive.CKeepAlivePacket
import gay.sukumi.irc.packet.packet.impl.login.LoginErrorPacket
import gay.sukumi.irc.packet.packet.impl.login.LoginRequestPacket
import gay.sukumi.irc.packet.packet.impl.login.LoginSuccessPacket
import gay.sukumi.irc.packet.packet.impl.profile.CProfilePacket
import gay.sukumi.irc.packet.packet.impl.profile.SProfilePacket
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.timeout.ReadTimeoutException

/**
 * The chat client protocol
 * @author Lucy
 */
class Protocol : HydraProtocol() {
    /*
     * Add packets & packet listeners
     */
    init {
        registerPacket(LoginRequestPacket::class.java)
        registerPacket(LoginSuccessPacket::class.java)
        registerPacket(LoginErrorPacket::class.java)
        registerPacket(CMessagePacket::class.java)
        registerPacket(SMessagePacket::class.java)
        registerPacket(CProfilePacket::class.java)
        registerPacket(SProfilePacket::class.java)
        registerPacket(SKeepAlivePacket::class.java)
        registerPacket(CKeepAlivePacket::class.java)
        registerListener(PacketHandler())
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        if(cause is ReadTimeoutException) {
            ChatServer.LOGGER.info("Disconnected '" + ChatServer.INSTANCE.getProfileByChannel(ctx.channel()).username + "' for not sending keep-alive packets")
            return
        }
        cause.printStackTrace()
        super.exceptionCaught(ctx, cause)
    }

}