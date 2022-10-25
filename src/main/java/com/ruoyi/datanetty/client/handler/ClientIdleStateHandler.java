package com.ruoyi.datanetty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;


public class ClientIdleStateHandler extends ChannelInboundHandlerAdapter {

    /**
     * 一段时间没有向服务器写数据，则发送ping包
     **/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                // write heartbeat to server
                ctx.channel().pipeline().fireChannelInactive();
//                ctx.channel().writeAndFlush(MyMessage.PingMessage())
//                        .addListener(f -> {
//                            log.debug("[写超时事件]发送心跳包" + (f.isSuccess() ? "成功" : "失败"));
//                        });
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
