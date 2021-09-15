package io.netty.example.echo.mytest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.UnsupportedEncodingException;

public class LengthFieldBasedFrameDecoderTest {

    public static void writeMessage(ByteBuf buffer, String message) throws UnsupportedEncodingException {
        byte[] bytes = message.getBytes("utf-8");
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes, 0, bytes.length);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        LengthFieldBasedFrameDecoder lengthFieldBasedFrameDecoder = new LengthFieldBasedFrameDecoder(64, 0, 4, 0, 4);
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
        StringDecoder stringDecoder = new StringDecoder();
//        StringEncoder stringEncoder = new StringEncoder();

        EmbeddedChannel channel = new EmbeddedChannel(lengthFieldBasedFrameDecoder, stringDecoder, loggingHandler);


        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        writeMessage(buffer, "hello");
        writeMessage(buffer, "你好,世界");
        writeMessage(buffer, "我是张三");
        channel.writeInbound(buffer);

    }
}
