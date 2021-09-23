package io.netty.chatroom.common.command;

import java.io.Serializable;

public class GroupChatCommand extends AbstractCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private String groupName;

    private String message;

    protected GroupChatCommand() {
        super(GROUP_CHAT_COMMAND);
    }

    public GroupChatCommand(String groupName, String message) {
        super(GROUP_CHAT_COMMAND);
        this.groupName = groupName;
        this.message = message;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
