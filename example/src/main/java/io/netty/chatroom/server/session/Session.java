package io.netty.chatroom.server.session;

import io.netty.channel.Channel;

public class Session {

    private Channel channel;

    private String username;

    private Session() {

    }

    public Session(String username, Channel channel) {
        this.username = username;
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getUsername() {
        return username;
    }

}
