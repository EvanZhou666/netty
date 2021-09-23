package io.netty.chatroom.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.chatroom.common.response.ChatMessage;
import io.netty.chatroom.common.response.GroupChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class GroupChatHandler extends SimpleChannelInboundHandler<GroupChatResponse> {

    private static final Logger logger = LoggerFactory.getLogger(GroupChatHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatResponse msg) throws Exception {
        logger.info("群组名`{}`-`{}`说：\"{}\"", msg.getGroupName(), msg.getFrom(), msg.getMessage());
    }

}