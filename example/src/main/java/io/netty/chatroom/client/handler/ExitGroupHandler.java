package io.netty.chatroom.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.chatroom.common.response.ExitGroupResponse;
import io.netty.chatroom.common.response.JoinGroupResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ExitGroupHandler extends SimpleChannelInboundHandler<ExitGroupResponse> {

    private static final Logger logger = LoggerFactory.getLogger(ExitGroupHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExitGroupResponse msg) throws Exception {

        if (msg.isSuccess()) {
            logger.info(msg.getMessage());
        } else {
            logger.error(msg.getMessage());
        }
    }

}
