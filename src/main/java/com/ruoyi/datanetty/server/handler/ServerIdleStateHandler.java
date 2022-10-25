package com.ruoyi.datanetty.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 监听心跳信息
 * @author huayalong
 *
 */
public class ServerIdleStateHandler extends ChannelInboundHandlerAdapter {
	
	protected final Logger logger = LoggerFactory.getLogger(ServerIdleStateHandler.class);
	
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
            	logger.debug("======================================================================================");
            	logger.debug("| !!! Heart Read timeout !!!  client may be offline, connection will be closed soon. |");
            	logger.debug("======================================================================================");
                ctx.disconnect();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
