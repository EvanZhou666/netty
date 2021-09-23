package io.netty.chatroom.common.response;

import java.io.Serializable;

public class GroupChatResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String from;

    private String groupName;

    private String message;

    public GroupChatResponse() {
    }

    public GroupChatResponse(String from, String message, String groupName) {
        this.from = from;
        this.message = message;
        this.groupName = groupName;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getGroupName() {
        return groupName;
    }
}