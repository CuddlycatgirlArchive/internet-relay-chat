package gay.sukumi.irc.packet.protocol

import gay.sukumi.hydra.shared.protocol.HydraProtocol
import gay.sukumi.irc.packet.packet.PacketHandler
import gay.sukumi.irc.packet.packet.impl.KeepAlivePacket
import gay.sukumi.irc.packet.packet.impl.chat.CMessagePacket
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket
import gay.sukumi.irc.packet.packet.impl.login.LoginErrorPacket
import gay.sukumi.irc.packet.packet.impl.login.LoginRequestPacket
import gay.sukumi.irc.packet.packet.impl.login.LoginSuccessPacket
import gay.sukumi.irc.packet.packet.impl.profile.CProfilePacket
import gay.sukumi.irc.packet.packet.impl.profile.SProfilePacket

/**
 * The chat client protocol
 * @author kittyuwu
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
        registerPacket(KeepAlivePacket::class.java)
        registerListener(PacketHandler())
    }
}