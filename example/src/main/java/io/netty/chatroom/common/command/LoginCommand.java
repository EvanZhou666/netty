package io.netty.chatroom.common.command;

import java.io.Serializable;

/**
 * 登陆指令
 */
public class LoginCommand extends AbstractCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    public LoginCommand() {
        super(LOGIN_COMMAND);
    }


    public LoginCommand(String username, String password) {
        super(LOGIN_COMMAND);
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
