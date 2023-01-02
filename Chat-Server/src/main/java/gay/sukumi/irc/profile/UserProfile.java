package gay.sukumi.irc.profile;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {

    private String username;
    private String uuid;
    private String mcUsername;
    private String currentServer;
    private Rank rank;
    private Client client;
    private List<String> permissions = new ArrayList<>();

    public UserProfile() {
    }

    public UserProfile(String username, String uuid, String mcUsername, String currentServer, Rank rank, Client client) {
        this.username = username;
        this.uuid = uuid;
        this.mcUsername = mcUsername;
        this.currentServer = currentServer;
        this.rank = rank;
        this.client = client;
    }

    public String getUsername() {
        return username;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMcUsername() {
        return mcUsername;
    }

    public void setMcUsername(String mcUsername) {
        this.mcUsername = mcUsername;
    }

    public String getCurrentServer() {
        return currentServer;
    }

    public void setCurrentServer(String currentServer) {
        this.currentServer = currentServer;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public enum Rank {
        ADMIN, USER
    }

    public enum Client {

        SUKUMI("Sukumi"), VOLT("Volt"), VELOCITY("Velocity"), ETERNAL("Eternal");

        final String name;

        Client(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
