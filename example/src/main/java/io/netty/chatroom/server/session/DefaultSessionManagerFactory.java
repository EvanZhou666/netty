package io.netty.chatroom.server.session;


public class DefaultSessionManagerFactory implements SessionManagerFactory {


    @Override
    public SessionManager getSessionManager() {
        return SessionManagerHolder.sessionManager;
    }

    private static class SessionManagerHolder {
        private static SessionManager sessionManager = new InMemorySessionManager();
    }

}
