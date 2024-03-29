package gay.sukumi.irc.database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Collation
import com.mongodb.client.model.CollationStrength
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates
import gay.sukumi.irc.profile.UserProfile.Rank
import org.apache.logging.log4j.LogManager
import org.bson.Document
import org.bson.conversions.Bson
import java.util.logging.Level
import java.util.logging.Logger
import java.util.regex.Pattern


class Database {

    lateinit var mongoClient: MongoClient
        private set

    lateinit var mongoDB: MongoDatabase
        private set

    fun connect() {
        // Disable logger
        val logger = Logger.getLogger("org.mongodb.driver")
        logger.level = Level.OFF

        // New mongodb client & get database
        mongoClient = MongoClients.create("mongodb://localhost:27017")
        mongoDB = mongoClient.getDatabase("irc")

        LogManager.getLogger("Database").info("Connection opened (type=mongodb)")
    }


    fun getUser(username: String): Account? {
        val usersCollection = mongoDB.getCollection("users")
        val collation = Collation.builder()
            .locale("en")
            .collationStrength(CollationStrength.SECONDARY)
            .build()

        val list = usersCollection.find(and(eq("username", username)))
            .collation(collation)
            .toList()
        val document =
            usersCollection.find(Document("username", Pattern.compile(".*$username.*", Pattern.CASE_INSENSITIVE)))
                .first()
        if (document != null) {
            val account = Account(
                document.getString("username"),
                document.getString("password"),
                Rank.valueOf(document.getString("rank")),
                document.getBoolean("muted"),
                document.getBoolean("banned"),
                document.getString("group"),
            )
            account.permissions = document.getList("permissions", String::class.java)
            return account
        }
        return null
    }

    fun editUser(account: Account) {
        val usersCollection = mongoDB.getCollection("users")
        val query = Document().append("username", account.username)
        val updates: Bson = Updates.combine(
            Updates.set("password", account.password),
            Updates.set("muted", account.isMuted),
            Updates.set("banned", account.isBanned),
            Updates.set("rank", account.rank.name),
            Updates.set("group", account.group),
            Updates.set("permissions", account.permissions)
        )

        usersCollection.updateOne(query, updates)
    }

    fun removeUser(account: Account) {
        val usersCollection = mongoDB.getCollection("users")
        val query = Document().append("username", account.username)

        usersCollection.deleteOne(query)
    }

    fun createUser(account: Account) {
        val usersCollection = mongoDB.getCollection("users")
        val accounts = Document("username", account.username).append("password", account.password)
            .append("rank", account.rank.name).append("muted", account.isMuted).append("banned", account.isBanned).append("group", account.group).append("permissions", ArrayList<String>())
        usersCollection.insertOne(accounts)
    }

    companion object {
        @JvmField
        val INSTANCE = Database()
    }
}