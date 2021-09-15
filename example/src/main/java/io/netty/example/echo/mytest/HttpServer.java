package io.netty.example.echo.mytest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaderNames.*;

public class HttpServer {

    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

    static Logger logger = LoggerFactory.getLogger(NioServer.class);

    public static void main(String[] args) throws InterruptedException {

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
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
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(new HttpServerCodec());
//                            p.addLast(new ChannelInboundHandlerAdapter() {
//                                @Override
//                                public void channelRead(ChannelHandlerContext ctx, Object msg) {
//                                    // HttpServerCodec会将我们的请求解析成两个对象，一个是DefaultHttpRequest请求头对象，一个是LastHttpContent$1 http请求内容对象
//                                    logger.info("msg type {}, msg is {}", msg.getClass(), msg.toString());
//                                }
//
//                                @Override
//                                public void channelReadComplete(ChannelHandlerContext ctx) {
//                                    ctx.flush();
//                                }
//
//                                @Override
//                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//                                    cause.printStackTrace();
//                                    ctx.close();
//                                }
//                            });

                            p.addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                    logger.info("{},{}", msg.uri(), msg.getClass());
                                    QueryStringDecoder queryString = new QueryStringDecoder(msg.uri(), Charset.forName("utf-8"));
                                    DefaultFullHttpResponse defaultHttpResponse = new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                                    String content = String.format("<h1>Hello world %s</h1>", queryString.parameters().get("name"));

                                    // 不加响应长度，浏览器地址栏会一直"转圈"，浏览器认为消息还没有接收完
                                    defaultHttpResponse.headers().setInt(CONTENT_LENGTH, content.getBytes().length);
                                    // content-type: text/html; charset=UTF-8
                                    defaultHttpResponse.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
                                    defaultHttpResponse.headers().set(CONTENT_ENCODING, "UTF-8");
                                    defaultHttpResponse.content().writeBytes(content.getBytes("utf-8"));
                                    ctx.writeAndFlush(defaultHttpResponse);
                                }
                            });
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
