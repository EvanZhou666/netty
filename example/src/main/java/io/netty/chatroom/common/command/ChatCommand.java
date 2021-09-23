package io.netty.chatroom.common.command;

import java.io.Serializable;

public class ChatCommand extends AbstractCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    public ChatCommand() {
        super(PRIVATE_CHAT_COMMAND);
    }

    public ChatCommand(String toUsername,String message) {
        super(PRIVATE_CHAT_COMMAND);
        this.toUsername = toUsername;
        this.message = message;
    }

    private String toUsername;

    private String message;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getToUsername() {
        return toUsername;
    }

    public String getMessage() {
        return message;
    }

}
