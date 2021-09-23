package io.netty.chatroom.common.command;

import java.io.Serializable;

public class ExitGroupCommand extends AbstractCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    public ExitGroupCommand() {
        super(EXIT_GROUP_COMMAND);
    }

    public ExitGroupCommand(String groupName) {
        super(EXIT_GROUP_COMMAND);
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
