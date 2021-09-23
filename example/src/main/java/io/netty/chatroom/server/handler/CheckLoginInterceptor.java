package io.netty.chatroom.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.chatroom.common.command.LoginCommand;
import io.netty.chatroom.common.response.LoginResponse;
import io.netty.chatroom.server.session.Session;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * 检查是否登陆过拦截器
 */
@ChannelHandler.Sharable
public class CheckLoginInterceptor extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 登陆请求，直接放行
        if (msg instanceof LoginCommand) {
            ctx.fireChannelRead(msg);
        }

        AttributeKey<Session> sessionAttributeKey = AttributeKey.valueOf("auth");
        Attribute<Session> attr = ctx.channel().attr(sessionAttributeKey);
        if (attr.get() == null) {
            ctx.channel().writeAndFlush(LoginResponse.NOT_LOGIN);
        } else {
            ctx.fireChannelRead(msg);
        }

    }

}
