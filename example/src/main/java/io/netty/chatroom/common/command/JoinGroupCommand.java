package io.netty.chatroom.common.command;

import java.io.Serializable;

public class JoinGroupCommand extends AbstractCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    public JoinGroupCommand() {
        super(JOIN_GROUP_COMMAND);
    }

    public JoinGroupCommand(String groupName) {
        super(JOIN_GROUP_COMMAND);
        this.groupName = groupName;
    }

    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
