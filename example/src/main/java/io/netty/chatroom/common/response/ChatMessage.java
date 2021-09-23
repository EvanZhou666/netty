package io.netty.chatroom.common.response;


import java.io.Serializable;

public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String from;

    private String message;

    public ChatMessage() {
    }

    public ChatMessage(String from, String message) {
        this.from = from;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }
}
