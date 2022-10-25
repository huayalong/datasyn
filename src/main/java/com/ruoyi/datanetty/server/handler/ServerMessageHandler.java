package com.ruoyi.datanetty.server.handler;


import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.datanetty.ChannelMap;
import com.ruoyi.datanetty.protocal.BinlogMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 负责跟客户端收发消息
 * @author huayalong
 *
 */
public class ServerMessageHandler extends SimpleChannelInboundHandler<BinlogMessage> {

	protected static final Logger logger = LoggerFactory.getLogger(ServerMessageHandler.class);
	
	/**
	 * 读取客户端发送过来的消息
	 */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinlogMessage msg) throws Exception {
    	
        logger.debug("Client message： type："+msg.getType()+", body length："+msg.getContentLength()+", body："+ msg.getContent());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	
    	InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
    	logger.error("服务器读取数据异常,客户端IP地址是：" + clientIp);
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
    
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    	
        //保存当前链接,应该在每个客户端链接过来的时候，传送自己的ID 用ID来作为UUID
        InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        String clientIp = ipSocket.getAddress().getHostAddress();
        ChannelMap.addChannel(clientIp, ctx.channel());
        logger.debug(new Date() + "客户端：" + clientIp + "已经连接！！！");
        
    }
    
	//发送数据
	public static void sendMessage(String ip,String message){

        ConcurrentHashMap<String, Channel> channelHashMap = ChannelMap.getChannelHashMap();
        
		Channel channel =  channelHashMap.get(ip);
        if(channel == null || !channel.isActive()){
			channelHashMap.remove(ip);
			logger.debug("该客户端:{}链接已中断",ip);
		}
        channel.writeAndFlush(BinlogMessage.StringMessage(message));
	}

}
