package com.ruoyi.datanetty.protocal;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class MyMessageDecoder extends ByteToMessageDecoder {

    private static final Logger log = LoggerFactory.getLogger(MyMessageDecoder.class);

    /*
    +---------------------------------------------------------------+
    | 魔数 2byte    |     指令类型 1byte      | 数据长度 4byte         |
    +---------------------------------------------------------------+
    |                   数据内容 （长度不定）                          |
    +---------------------------------------------------------------+
    */

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> messages) throws Exception {

        // 魔数
        final int magic = byteBuf.readShort();
        if (magic != ProtocolConst.PACKAGE_MAGIC_NUM) {
            log.error("数据包解析错误：魔数 {}", magic);
        }

        // 指令类型
        final byte opType = byteBuf.readByte();
        final OpType type = OpType.getOpType(opType);
        if (type == null) {
            log.error("未知操作类型：{}" + opType);
            return;
        }
        BinlogMessage msg = new BinlogMessage();
        msg.setType(type);
        int length = byteBuf.readInt();
        msg.setContentLength(length);
        if(length > 0){
            byte[] contents = new byte[length];
            byteBuf.readBytes(contents,0, length);
            ByteArrayInputStream bis=new ByteArrayInputStream(contents);
            ObjectInputStream ois=new ObjectInputStream(bis);
            msg.setContent(ois.readObject());
        }
        messages.add(msg);

    }
}
