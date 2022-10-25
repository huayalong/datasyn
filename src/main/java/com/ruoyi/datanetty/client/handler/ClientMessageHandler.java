package com.ruoyi.datanetty.client.handler;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.datanetty.protocal.BinlogMessage;
import com.ruoyi.datasync.ProcessMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 接收服务器推送过来的binlog消息
 * @author huayalong
 *
 */
public class ClientMessageHandler extends SimpleChannelInboundHandler<BinlogMessage> {

	protected final Logger logger = LoggerFactory.getLogger(ClientMessageHandler.class);
	
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinlogMessage msg) throws Exception {
    	
    	InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
    	
    	logger.debug("Server Message：客户端IP：{}, type{}, body length：{}, body：{}",clientIp,msg.getType(),msg.getContentLength(),msg.getContent());
        ProcessMessage.doProcessMessage(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
