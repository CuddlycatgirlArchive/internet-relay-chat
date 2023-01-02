package gay.sukumi.irc.listener;

import gay.sukumi.hydra.shared.handler.Session;

public interface ConnectionListener {

    void onConnected(String host, Session session);
    void onDisconnected(String host, Session session);

}
