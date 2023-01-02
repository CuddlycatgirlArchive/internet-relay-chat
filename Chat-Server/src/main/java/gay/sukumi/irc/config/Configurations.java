package gay.sukumi.irc.config;

import java.util.ArrayList;
import java.util.HashMap;

public class Configurations {

    private int port = 8888;
    private String database = "mongodb://localhost:27015";
    private String ircMaster = "Lucy";
    private HashMap<String, ArrayList<String>> groups = new HashMap<>();


    public Configurations() {

    }

    public Configurations(int port, String database, String ircMaster) {
        this.port = port;
        this.database = database;
        this.ircMaster = ircMaster;
        ArrayList<String> groupPermissions = new ArrayList<>();
        groupPermissions.add("irc.admin.adduser");
        groupPermissions.add("irc.admin.rmuser");
        groupPermissions.add("irc.admin.mute");
        groupPermissions.add("irc.admin.unmute");
        groupPermissions.add("irc.admin.passwd");
        getGroups().put("master", groupPermissions);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getIrcMaster() {
        return ircMaster;
    }

    public void setIrcMaster(String ircMaster) {
        this.ircMaster = ircMaster;
    }

    public HashMap<String, ArrayList<String>> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, ArrayList<String>> groups) {
        this.groups = groups;
    }
}
