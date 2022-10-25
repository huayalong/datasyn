package com.ruoyi.datanetty.protocal;
/**
 * 消息类型枚举值
 * @author huayalong
 *
 */
public enum OpType {

    PING((byte)0, "客户端向服务器发送的心跳包"),
    PONG((byte)1, "服务器回复客户端的心跳包"),
    DATA((byte)2, "客户端向服务器发送的心跳包");

    private byte code;
    
    private String remark;

    private OpType(byte code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public byte type() {
        return this.code;
    }

    public static OpType getOpType(byte type) {
        for (OpType value : values()) {
            if (value.type() == type) {
                return value;
            }
        }
        return null;
    }
}
