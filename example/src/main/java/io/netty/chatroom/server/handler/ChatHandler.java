package io.netty.chatroom.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.chatroom.common.command.ChatCommand;
import io.netty.chatroom.common.response.ChatMessage;
import io.netty.chatroom.server.session.DefaultSessionManagerFactory;
import io.netty.chatroom.server.session.Session;
import io.netty.chatroom.server.session.SessionManager;
import io.netty.chatroom.server.session.SessionManagerFactory;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class ChatHandler extends SimpleChannelInboundHandler<ChatCommand> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatCommand msg) throws Exception {

        String receiver = msg.getToUsername();

        SessionManagerFactory sessionManagerFactory = new DefaultSessionManagerFactory();
        SessionManager sessionManager = sessionManagerFactory.getSessionManager();
        Session session = sessionManager.getSession(receiver);
        AttributeKey<Session> sessionAttributeKey = AttributeKey.valueOf("auth");
        Attribute<Session> attr = ctx.channel().attr(sessionAttributeKey);

        session.getChannel().writeAndFlush(new ChatMessage(attr.get().getUsername(), msg.getMessage()));
    }

}
