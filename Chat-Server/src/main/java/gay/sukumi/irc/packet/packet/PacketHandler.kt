package gay.sukumi.irc.packet.packet

import gay.sukumi.hydra.shared.handler.Session
import gay.sukumi.hydra.shared.protocol.packets.listener.Handler
import gay.sukumi.hydra.shared.protocol.packets.listener.HydraPacketListener
import gay.sukumi.irc.ChatServer
import gay.sukumi.irc.database.Database
import gay.sukumi.irc.packet.packet.impl.chat.CMessagePacket
import gay.sukumi.irc.packet.packet.impl.chat.SMessagePacket
import gay.sukumi.irc.packet.packet.impl.keepalive.CKeepAlivePacket
import gay.sukumi.irc.packet.packet.impl.keepalive.SKeepAlivePacket
import gay.sukumi.irc.packet.packet.impl.login.LoginErrorPacket
import gay.sukumi.irc.packet.packet.impl.login.LoginRequestPacket
import gay.sukumi.irc.packet.packet.impl.login.LoginSuccessPacket
import gay.sukumi.irc.packet.packet.impl.profile.CProfilePacket
import gay.sukumi.irc.packet.packet.impl.profile.SProfilePacket
import gay.sukumi.irc.profile.UserProfile
import gay.sukumi.irc.utils.Errors
import java.util.*
import java.util.function.Consumer

/**
 * I don't feel like commenting this too much.
 *
 * @author Lucy
 */
@Suppress("unused")
class PacketHandler : HydraPacketListener {

    /**
     * This function will be executed when the server receives a login request packet.
     *
     * @param packet  Login success packet itself
     * @param session Client session
     */
    @Handler
    fun onPacket(packet: LoginRequestPacket, session: Session) {
        val account = Database.INSTANCE.getUser(packet.username)
        if (account == null) {
            ChatServer.LOGGER.info("Failed login attempt from '" + session.address.toString() + "' (User not existing)")
            session.send(LoginErrorPacket(Errors.INVALID_PASSWORD))
            return
        }

        if (!account.password.equals(packet.password, ignoreCase = false)) {
            ChatServer.LOGGER.info("Failed login attempt from '" + session.address.toString() + "' (Password invalid)")
            session.send(LoginErrorPacket(Errors.INVALID_PASSWORD))
            return
        }

        if (account.isBanned) {
            session.send(LoginErrorPacket(Errors.BANNED))
            return
        }

        val userProfile = UserProfile(
            account.username,
            UUID.randomUUID().toString(),
            packet.mcUsername,
            "",
            account.rank,
            packet.client
        )

        session.send(LoginSuccessPacket(userProfile))

        // Broadcast funny packet
        ChatServer.INSTANCE.profileHashMap.values.forEach(Consumer { profile: UserProfile? ->
            session.send(SProfilePacket(SProfilePacket.Action.ADD, profile))
        })

        ChatServer.INSTANCE.profileHashMap[session] = userProfile
        ChatServer.INSTANCE.broadcastPacket(SProfilePacket(SProfilePacket.Action.ADD, userProfile))

        ChatServer.LOGGER.info(String.format("User '%s' logged in", userProfile.username))
    }

    /**
     * This function will be executed when a user updates their profile.
     *
     * @param packet  Profile update packet itself
     * @param session Client session
     */
    @Handler
    fun onPacket(packet: CProfilePacket, session: Session?) {
        when (packet.action) {
            CProfilePacket.Action.CHANGE_SERVER -> {
                // Get new & old profile
                val newUserProfile = ChatServer.INSTANCE.profileHashMap[session]

                // Set current server
                newUserProfile!!.currentServer = packet.profile.currentServer

                // Broadcast packet
                ChatServer.INSTANCE.broadcastPacket(
                    SProfilePacket(
                        SProfilePacket.Action.CHANGE_SERVER,
                        newUserProfile
                    )
                )
                ChatServer.USERPROFILE_LOGGER.info(
                    String.format(
                        "User '%s' changed their server to '%s'",
                        newUserProfile.username,
                        newUserProfile.currentServer
                    )
                )
            }

            CProfilePacket.Action.CHANGE_NAME -> {
                // Get new & old profile
                val newUserProfile = ChatServer.INSTANCE.profileHashMap[session]

                // Set mc username
                newUserProfile!!.mcUsername = packet.profile.mcUsername

                // Broadcast packet
                ChatServer.INSTANCE.broadcastPacket(
                    SProfilePacket(
                        SProfilePacket.Action.CHANGE_NAME,
                        newUserProfile
                    )
                )

                ChatServer.USERPROFILE_LOGGER.info(
                    String.format(
                        "User '%s' changed their mc username to '%s'",
                        newUserProfile.username,
                        newUserProfile.mcUsername
                    )
                )
            }

            else -> {}
        }
    }

    /**
     * This function will be executed when a user sends a message.
     *
     * @param packet  Chat message packet itself
     * @param session Client session
     */
    @Handler
    fun onPacket(packet: CMessagePacket, session: Session) {
        val userProfile = ChatServer.INSTANCE.getProfileByUUID(packet.uuid)
        val account = Database.INSTANCE.getUser(userProfile.username) ?: return

        if (packet.message.isEmpty()) {
            return
        }

        if (packet.message.startsWith("/")) {
            ChatServer.INSTANCE.commandRegistry.runCommand(session, userProfile, packet.message)
            return
        }

        if (account.isMuted) {
            session.send(SMessagePacket("You're currently muted."))
            return
        }

        ChatServer.INSTANCE.broadcastPacket(SMessagePacket(userProfile, SMessagePacket.Type.USER, packet.message))
    }

    @Handler
    fun onPacket(packet: CKeepAlivePacket, session: Session) {
        session.send(SKeepAlivePacket())
    }
}