package io.netty.chatroom.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.chatroom.common.command.GroupChatCommand;
import io.netty.chatroom.common.response.GroupChatResponse;
import io.netty.chatroom.server.service.GroupService;
import io.netty.chatroom.server.service.impl.GroupServiceImpl;
import io.netty.chatroom.server.session.DefaultSessionManagerFactory;
import io.netty.chatroom.server.session.Session;
import io.netty.chatroom.server.session.SessionManager;

import java.util.Set;

@ChannelHandler.Sharable
public class GroupChatHandler extends BaseChannelInBoundHandler<GroupChatCommand> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatCommand msg) throws Exception {
        String sender = getSession(ctx).getUsername();

        GroupService groupService = GroupServiceImpl.DEFAULT;
        Set<String> members = groupService.members(msg.getGroupName());

        SessionManager sessionManager = new DefaultSessionManagerFactory().getSessionManager();

        for (String member : members) {
            Session session = sessionManager.getSession(member);
            session.getChannel().writeAndFlush(new GroupChatResponse(sender, msg.getMessage(), msg.getGroupName()));
        }
    }

}
