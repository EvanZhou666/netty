package io.netty.chatroom.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.chatroom.common.command.JoinGroupCommand;
import io.netty.chatroom.common.response.JoinGroupResponse;
import io.netty.chatroom.server.service.GroupService;
import io.netty.chatroom.server.service.impl.GroupServiceImpl;
import io.netty.chatroom.server.session.Session;

public class JoinGroupHandler extends BaseChannelInBoundHandler<JoinGroupCommand> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupCommand msg) throws Exception {
        Session session = getSession(ctx);
        String username = session.getUsername();
        GroupService groupService = GroupServiceImpl.DEFAULT;
        groupService.joinGroup(username, msg.getGroupName());
        ctx.writeAndFlush(new JoinGroupResponse(0, "success"));
    }

}
