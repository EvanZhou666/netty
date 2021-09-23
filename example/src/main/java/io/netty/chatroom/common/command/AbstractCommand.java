package io.netty.chatroom.common.command;

import java.io.Serializable;

public abstract class AbstractCommand implements Serializable {

    private static final long serialVersionUID = 42L;

    protected byte commandType;

    private AbstractCommand () {
    }

    protected AbstractCommand(byte commandType) {
        this.commandType = commandType;
    }

//    protected abstract byte getCommandType();

    // 登陆
    public static byte LOGIN_COMMAND = 1;

    // 退出登陆
    public static byte LOGIN_OUT_COMMAND = 2;

    // 两个人聊天
    public static byte PRIVATE_CHAT_COMMAND = 3;

    // 加入群组
    public static byte JOIN_GROUP_COMMAND = 4;

    // 离开群组
    public static byte EXIT_GROUP_COMMAND = 5;

    // 群聊
    public static byte GROUP_CHAT_COMMAND = 6;

}
