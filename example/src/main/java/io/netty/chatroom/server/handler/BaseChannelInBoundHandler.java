package io.netty.chatroom.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.chatroom.server.session.Session;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public abstract class BaseChannelInBoundHandler<T> extends SimpleChannelInboundHandler<T> {

    private final AttributeKey<Session> sessionAttributeKey = AttributeKey.valueOf("auth");

    protected Session getSession(ChannelHandlerContext ctx) {
        Attribute<Session> attr = ctx.channel().attr(sessionAttributeKey);
        return attr.get();
    }

}
