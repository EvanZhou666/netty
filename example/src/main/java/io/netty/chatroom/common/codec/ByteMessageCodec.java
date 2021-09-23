//package io.netty.chatroom.common.codec;
//
//import com.sun.org.slf4j.internal.Logger;
//import com.sun.org.slf4j.internal.LoggerFactory;
//import io.netty.buffer.ByteBuf;
//
//import java.io.UnsupportedEncodingException;
//
//public class ByteMessageCodec {
//
//    private static final Logger log = LoggerFactory.getLogger(ByteMessageCodec.class);
//
//    public static void writeMessage(ByteBuf buffer, String message) {
//        try {
//            writeMessage0(buffer, message);
//        } catch (UnsupportedEncodingException e) {
//            log.error("",e);
//        }
//    }
//
//    public static void writeMessage0(ByteBuf buffer, String message) throws UnsupportedEncodingException {
//        byte[] bytes = message.getBytes("utf-8");
//        buffer.writeInt(bytes.length);
//        buffer.writeBytes(bytes, 0, bytes.length);
//    }
//
//}
