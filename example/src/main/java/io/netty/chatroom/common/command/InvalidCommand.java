package io.netty.chatroom.common.command;

import java.io.Serializable;

public class InvalidCommand extends AbstractCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final InvalidCommand INVALID_COMMAND = new InvalidCommand();

    private InvalidCommand() {
        super((byte) -1);
    }
}
