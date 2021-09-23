package io.netty.chatroom.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.chatroom.common.command.LoginCommand;
import io.netty.chatroom.common.response.LoginResponse;
import io.netty.chatroom.server.session.DefaultSessionManagerFactory;
import io.netty.chatroom.server.session.Session;
import io.netty.chatroom.server.session.SessionManager;
import io.netty.chatroom.server.session.SessionManagerFactory;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginCommand> {

    private final AttributeKey<Session> sessionAttributeKey = AttributeKey.valueOf("auth");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginCommand command) throws Exception {

        if ("zhangsan".equals(command.getUsername()) && "123".equals(command.getPassword()) || ("lisi".equals(command.getUsername()) && "123".equals(command.getPassword()))) {
            Session session = new Session(command.getUsername(), ctx.channel());
            SessionManagerFactory sessionManagerFactory = new DefaultSessionManagerFactory();
            SessionManager sessionManager = sessionManagerFactory.getSessionManager();
            sessionManager.saveSession(command.getUsername(), session);
            Attribute<Session> attr = ctx.channel().attr(sessionAttributeKey);
            attr.set(session);
            ctx.channel().writeAndFlush(new LoginResponse(0, "success"));
        } else {
            ctx.channel().writeAndFlush(new LoginResponse(-1, "用户名或密码不正确"));
        }
    }

}
