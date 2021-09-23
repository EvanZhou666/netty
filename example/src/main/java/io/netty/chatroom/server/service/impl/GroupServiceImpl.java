package io.netty.chatroom.server.service.impl;

import io.netty.chatroom.server.service.GroupService;
import io.netty.util.internal.ConcurrentSet;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GroupServiceImpl implements GroupService {

    private Map<String, Set<String>> map = new ConcurrentHashMap<String, Set<String>>(16);

    public static final GroupService DEFAULT = new GroupServiceImpl();

    private GroupServiceImpl() {

    }

    @Override
    public void joinGroup(String joinerName, String groupName) {
        if (map.get(groupName) == null) {
            map.put(groupName, new ConcurrentSet<String>());
        }
        map.get(groupName).add(joinerName);
    }

    @Override
    public void exitGroup(String joinerName, String groupName) {

        if (map.get(groupName) != null) {
            map.get(groupName).remove(joinerName);
        }
    }

    @Override
    public Set<String> members(String groupName) {
        return map.get(groupName);
    }

}
