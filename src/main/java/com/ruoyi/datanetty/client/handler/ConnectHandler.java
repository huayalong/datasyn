package com.ruoyi.datanetty.client.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.datanetty.client.NettyClient;
import com.ruoyi.datanetty.client.config.ClientCfg;
import com.ruoyi.datanetty.client.retry.DefaultRetryPolicy;
import com.ruoyi.datanetty.client.retry.RetryPolicy;
import com.ruoyi.datanetty.protocal.BinlogMessage;
import com.ruoyi.datanetty.protocal.OpType;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.ScheduledFuture;

@ChannelHandler.Sharable
public class ConnectHandler extends SimpleChannelInboundHandler<BinlogMessage> {

	protected final Logger logger = LoggerFactory.getLogger(ConnectHandler.class);
	
    private int retries = 0;

    private RetryPolicy retryPolicy;

    private NettyClient nettyClient;

    public ConnectHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
        this.retryPolicy = new DefaultRetryPolicy();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	logger.debug("========================================================================");
    	logger.debug("| [SUCCESS]     Server has been connected                              |");
    	logger.debug("========================================================================");
        retries = 0;
        ctx.fireChannelActive();
        super.channelActive(ctx);
        sendPing(ctx.channel());
    }

    private void sendPing(Channel channel) {

        ScheduledFuture<?> future = channel.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                channel.writeAndFlush(BinlogMessage.PingMessage())
                        .addListener(f -> {
                        	logger.debug("发送心跳包" + (f.isSuccess() ? "成功" : "失败"));
                            if (f.isSuccess()) {
                                sendPing(channel);
                            }
                        });
                    }
        }, ClientCfg.HEART_INTERVAL, TimeUnit.SECONDS);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (retries == 0) {
        	logger.debug("========================================================================");
        	logger.debug("|  [FAILURE]   Server has been disconnected ...                        |");
        	logger.debug("========================================================================");
            ctx.disconnect();
            ctx.close();
        }

        boolean allowRetry = retryPolicy.willRetry(++retries);
        if (allowRetry) {

            long sleepSecond = retryPolicy.retryInterval(retries);
            logger.debug("Try connecting to the server for {} time in {} s",retries,sleepSecond);

            final EventLoop eventLoop = ctx.channel().eventLoop();
            eventLoop.schedule(() -> {
                nettyClient.connect();
            }, sleepSecond, TimeUnit.SECONDS);
            ctx.fireChannelInactive();
        } else {
        	System.out.println("Unable to connect after {"+(retries - 1)+"} attempts, client will close soon ...");
            nettyClient.shutdown();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当Channel已经断开的情况下, 仍然发送数据, 会抛异常, 该方法会被调用.
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinlogMessage msg) throws Exception {
        if (OpType.PONG == msg.getType()) {
        	logger.debug("服务器回复：类型：{},长度:{},内容:{} ",msg.getType(),msg.getContentLength(),msg.getContent());
        }else{
        	 ctx.fireChannelRead(msg);
        }
    }
}
