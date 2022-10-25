package com.ruoyi.datanetty.server.config;

public class ServerCfg {

    /**
     * 心跳超时时间(s)
     *
     * <p>
     *     超过此时间未收到客户端心跳，则任务客户端已离线，服务器会断开通道
     * </p>
     **/
    public static int HEART_READ_TIME = 15;

}
