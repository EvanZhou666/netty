package io.netty.chatroom.common.response;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    public static LoginResponse NOT_LOGIN = new LoginResponse(400, "请先登陆");

    // 0 成功 -1失败
    private int code;

    private String message;

    public LoginResponse() {

    }

    public LoginResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
