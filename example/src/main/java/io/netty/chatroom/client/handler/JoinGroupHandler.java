package io.netty.chatroom.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.chatroom.common.response.JoinGroupResponse;
import io.netty.chatroom.common.response.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class JoinGroupHandler extends SimpleChannelInboundHandler<JoinGroupResponse> {

    private static final Logger logger = LoggerFactory.getLogger(JoinGroupHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponse msg) throws Exception {

        if (msg.isSuccess()) {
            logger.info("加入群聊成功🎉🎉🎉");
        } else {
            logger.error(msg.getMessage());
        }
    }

}
