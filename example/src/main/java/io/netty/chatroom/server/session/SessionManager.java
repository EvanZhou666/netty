package io.netty.chatroom.server.session;

public interface SessionManager {

    /**
     * 获取session
     * @param username
     * @return
     */
    Session getSession(String username);

    /**
     * 保存session
     * @param username
     * @param session
     */
    void saveSession(String username,Session session);

}
