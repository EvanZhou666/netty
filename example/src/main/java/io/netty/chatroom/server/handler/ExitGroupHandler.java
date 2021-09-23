package io.netty.chatroom.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.chatroom.common.command.ExitGroupCommand;
import io.netty.chatroom.common.command.GroupChatCommand;
import io.netty.chatroom.common.response.ExitGroupResponse;
import io.netty.chatroom.common.response.GroupChatResponse;
import io.netty.chatroom.server.service.GroupService;
import io.netty.chatroom.server.service.impl.GroupServiceImpl;
import io.netty.chatroom.server.session.DefaultSessionManagerFactory;
import io.netty.chatroom.server.session.Session;
import io.netty.chatroom.server.session.SessionManager;

import java.util.Set;

@ChannelHandler.Sharable
public class ExitGroupHandler extends BaseChannelInBoundHandler<ExitGroupCommand> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExitGroupCommand msg) throws Exception {
        String wantToExitUserName = getSession(ctx).getUsername();
        GroupService groupService = GroupServiceImpl.DEFAULT;
        groupService.exitGroup(wantToExitUserName, msg.getGroupName());
        SessionManager sessionManager = new DefaultSessionManagerFactory().getSessionManager();
        sessionManager.getSession(wantToExitUserName).getChannel().writeAndFlush(new ExitGroupResponse(-1, String.format("退出`%s`群成功", msg.getGroupName())));
    }
}
