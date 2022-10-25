package com.ruoyi.datanetty.server.handler;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.datanetty.protocal.BinlogMessage;
import com.ruoyi.datanetty.protocal.OpType;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 连接handler 监控客户端和服务的连接
 * @author huayalong
 *
 */
public class ConnectHandler extends ChannelInboundHandlerAdapter {

    private volatile AtomicInteger onlineCount = new AtomicInteger(0);
    
    protected final Logger logger = LoggerFactory.getLogger(ConnectHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msgObj) throws Exception {
    	
        BinlogMessage msg = (BinlogMessage) msgObj;
        //处理心跳信息和普通数据消息
        if (OpType.PING == msg.getType() || OpType.PONG == msg.getType()) {
            ctx.writeAndFlush(BinlogMessage.PongMessage());
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final int count = onlineCount.incrementAndGet();
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final int count = onlineCount.decrementAndGet();
        ctx.fireChannelInactive();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	
	    InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
    	
    	logger.error("服务器读取数据异常,客户端IP:",clientIp);
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
