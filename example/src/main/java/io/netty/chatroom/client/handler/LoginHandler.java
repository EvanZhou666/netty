package io.netty.chatroom.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.chatroom.common.response.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginResponse> {

    private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse msg) throws Exception {

        if (msg.isSuccess()) {
            logger.info("登陆成功🎉🎉🎉");
        } else {
            logger.error(msg.getMessage());
        }
    }

}
