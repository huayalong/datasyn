package com.ruoyi.datanetty.protocal;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class MyMessageEncoder extends MessageToByteEncoder<BinlogMessage> {

    /*
    +---------------------------------------------------------------+
    | 魔数 2byte    |     报文类型 1byte      | 数据长度 4byte         |
    +---------------------------------------------------------------+
    |                   数据内容 （长度不定）                          |
    +---------------------------------------------------------------+
    */

    private static final Logger log = LoggerFactory.getLogger(MyMessageEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BinlogMessage myMessage, ByteBuf byteBuf) throws Exception {

        // 魔数
        byteBuf.writeShort(ProtocolConst.PACKAGE_MAGIC_NUM);

        // 指令类型
        byteBuf.writeByte(myMessage.getType().type());

        // 消息长度和数据内容
        final Object content = myMessage.getContent();
        if (content == null) {
            byteBuf.writeInt(0);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(content);
            byte[] bytes = bos.toByteArray();
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
        }
    }
}
