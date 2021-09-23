package io.netty.chatroom.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.chatroom.common.response.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatMessageHandler extends SimpleChannelInboundHandler<ChatMessage> {

    private static final Logger logger = LoggerFactory.getLogger(ChatMessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMessage msg) throws Exception {
        logger.info("`{}`对你说`{}`", msg.getFrom(), msg.getMessage());
    }

}