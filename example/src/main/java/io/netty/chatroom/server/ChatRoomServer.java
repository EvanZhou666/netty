package io.netty.chatroom.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.chatroom.server.handler.*;
import io.netty.chatroom.common.codec.ChatRoomByteToMessageCodec;
import io.netty.chatroom.common.codec.ProcotolFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 聊天室服务端
 */
public class ChatRoomServer {
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    static Logger logger = LoggerFactory.getLogger(ChatRoomServer.class);

    public static void main(String[] args) throws InterruptedException {

        // Configure the server. acceptor
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 不传线程数，默认是cpu核数的2倍
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)// 通过反射工厂创建NioServerSocketChannel实例
                    .option(ChannelOption.SO_BACKLOG, 100)// 指定创建channel时候的配置参数
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {// 设置channel上的处理器，处理请求
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new ProcotolFrameDecoder());
                            p.addLast(new LoggingHandler(LogLevel.DEBUG));
                            // 字符串解码器
                            p.addLast(new ChatRoomByteToMessageCodec());
//                            p.addLast(new ChannelInboundHandlerAdapter() {
//                                @Override
//                                public void channelRead(ChannelHandlerContext ctx, Object msg) {
//                                    logger.info(msg.toString());// ctx = ChannelHandlerContext
////                                    System.out.println(msg);
////                                    ctx.write(msg);
////                                    ctx.write("received your message");
//                                    ctx.fireChannelRead(msg);
//                                }
//                            });
                            p.addLast(new LoginHandler());
                            p.addLast(new CheckLoginInterceptor());
                            p.addLast(new ChatHandler());
                            p.addLast(new JoinGroupHandler());
                            p.addLast(new GroupChatHandler());
                            p.addLast(new ExitGroupHandler());
                        }
                    });

            // Start the server.
            ChannelFuture f = b.bind(PORT).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
