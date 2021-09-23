package io.netty.chatroom.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.chatroom.client.handler.*;
import io.netty.chatroom.common.codec.ChatRoomByteToMessageCodec;
import io.netty.chatroom.common.codec.ProcotolFrameDecoder;
import io.netty.chatroom.common.command.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * 聊天室客户端
 */
public class ChatRoomClient {

    private static Logger logger = LoggerFactory.getLogger(ChatRoomClient.class);

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new ProcotolFrameDecoder());
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(new ChatRoomByteToMessageCodec());
                            p.addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                    logger.info("response from server:" + msg);
                                    ctx.fireChannelRead(msg);
                                }

                                @Override
                                public void channelActive(final ChannelHandlerContext ctx) {
                                    logger.info("channel active");
                                    final ChannelPromise promise = ctx.newPromise();
                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            promise.setSuccess();
                                            Scanner scanner = new Scanner(System.in);
                                            while (scanner.hasNext()) {
                                                String msg = scanner.next();
                                                AbstractCommand command = executeCommand(msg);
                                                ctx.writeAndFlush(command);
                                            }
                                        }
                                    });
                                    thread.start();
                                    while (!promise.isDone()) {
                                        if (promise.isSuccess()) {
                                            break;
                                        }
                                    }
                                }
                            });
                            p.addLast(new LoginHandler());
                            p.addLast(new ChatMessageHandler());
                            p.addLast(new JoinGroupHandler());
                            p.addLast(new GroupChatHandler());
                            p.addLast(new ExitGroupHandler());

                        }
                    });

            ChannelFuture f = b.connect(HOST, PORT).sync();
            printTips();
            f.channel().closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    private static void printTips() {
        System.out.println("--------请输入指令执行操作--------");
        System.out.println("1:登陆");
        System.out.println("2:退出登陆");
        System.out.println("3:私聊-");
        System.out.println("4:加入群组");
        System.out.println("5:离开群组");
        System.out.println("6:群聊");
    }

    private static AbstractCommand executeCommand(String commandStr) {
        if (commandStr == null || "".equalsIgnoreCase(commandStr)) {
            return null;
        }

        AbstractCommand command = null;
        String[] commandSplit = commandStr.split("\\|");
        switch (Byte.parseByte(commandSplit[0])) {
            case 1:
                command = new LoginCommand(commandSplit[1], commandSplit[2]);
                break;
            case 2:
                command = new LoginCommand(commandSplit[1], commandSplit[2]);
                break;
            case 3:
                command = new ChatCommand(commandSplit[1], commandSplit[2]);
                break;
            case 4:
                // 4|love
                command = new JoinGroupCommand(commandSplit[1]);
                break;
            case 5:
                // 4|love
                command = new ExitGroupCommand(commandSplit[1]);
                break;
            case 6:
                // 6|love|嗨，兄弟们火锅走起!
                command = new GroupChatCommand(commandSplit[1], commandSplit[2]);
                break;
            default:
                break;
        }
        return command;

    }

}
