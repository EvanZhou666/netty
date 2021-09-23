package io.netty.chatroom.server.service;

import java.util.Set;

public interface GroupService {

    void joinGroup(String joinerName, String groupName);

    void exitGroup(String joinerName, String groupName);

    Set<String> members(String groupName);

}
