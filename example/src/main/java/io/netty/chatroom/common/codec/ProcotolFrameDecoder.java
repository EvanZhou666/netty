package io.netty.chatroom.common.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;


/**
 * Byte字节消息解码器
 */
public class ProcotolFrameDecoder extends LengthFieldBasedFrameDecoder {


    public ProcotolFrameDecoder() {
        super(1024, 0, 4, 0, 4);
    }

}
