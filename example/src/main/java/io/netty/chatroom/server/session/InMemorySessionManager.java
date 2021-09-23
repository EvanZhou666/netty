package io.netty.chatroom.server.session;

import java.util.HashMap;
import java.util.Map;

public class InMemorySessionManager implements SessionManager {

    Map<String, Session> sessionMap = new HashMap<String, Session>(16);

    @Override
    public Session getSession(String username) {
        return sessionMap.get(username);
    }

    @Override
    public void saveSession(String username, Session session) {
        sessionMap.put(username,session);
    }

}
