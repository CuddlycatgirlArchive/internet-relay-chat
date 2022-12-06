package gay.sukumi.database

import gay.sukumi.irc.profile.UserProfile

class Account(
    @JvmField var username: String,
    @JvmField var password: String,
    @JvmField var rank: UserProfile.Rank,
    var isMuted: Boolean,
    var isBanned: Boolean
)