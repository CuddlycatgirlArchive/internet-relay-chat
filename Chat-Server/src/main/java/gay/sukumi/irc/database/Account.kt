package gay.sukumi.irc.database

import gay.sukumi.irc.profile.UserProfile

class Account(
    var username: String,
    var password: String,
    var rank: UserProfile.Rank,
    var isMuted: Boolean,
    var isBanned: Boolean
)