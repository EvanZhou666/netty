package io.netty.chatroom.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 聊天室消息编解码器
 */
public class ChatRoomByteToMessageCodec extends ByteToMessageCodec {

    /**
     * 编码
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

        ByteArrayOutputStream byIns = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byIns);
        objectOutputStream.writeObject(msg);

        byte[] bytes = byIns.toByteArray();
        out.writeInt(bytes.length);
        out.writeBytes(bytes, 0, bytes.length);

//        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteInputStream(bytes, bytes.length));
//        Object o = objectInputStream.readObject();
//        System.out.println(o);
    }

    /**
     * 解码
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
        int len = in.readableBytes();
        byte[] bytes = new byte[len];
        in.readBytes(bytes, 0, len);
        ByteArrayInputStream ins = new ByteArrayInputStream(bytes);
        ObjectInputStream objs = new ObjectInputStream(ins);
        out.add(objs.readObject());
//        int lenght = in.getInt(0);
//        byte[] bytes = new byte[lenght];
//        in.readBytes(bytes, 4, lenght);
//        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteInputStream(bytes, lenght));
//        Object o = objectInputStream.readObject();
//        out.add(o);

    }


}
