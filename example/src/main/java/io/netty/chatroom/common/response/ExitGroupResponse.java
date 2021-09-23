package io.netty.chatroom.common.response;

import java.io.Serializable;

public class ExitGroupResponse implements Serializable {

    private static final long serialVersionUID = 1L;


    // 0 成功 -1失败
    private int code;

    private String message;

    public ExitGroupResponse() {

    }

    public ExitGroupResponse(int code, String message) {
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
