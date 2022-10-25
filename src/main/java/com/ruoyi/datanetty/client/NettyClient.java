package com.ruoyi.datanetty.client;

import com.ruoyi.datanetty.client.handler.ClientMessageHandler;
import com.ruoyi.datanetty.client.handler.ConnectHandler;
import com.ruoyi.datanetty.client.retry.RetryPolicy;
import com.ruoyi.datanetty.protocal.BinlogMessage;
import com.ruoyi.datanetty.protocal.MyMessageDecoder;
import com.ruoyi.datanetty.protocal.MyMessageEncoder;
import com.ruoyi.datanetty.protocal.ProtocolConst;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyClient {

    private String host;
    private int port;
    private Bootstrap bootstrap;
    private static Channel channel;
    private RetryPolicy retryPolicy;
    private ConnectHandler connectHandler = new ConnectHandler(NettyClient.this);
    EventLoopGroup group = new NioEventLoopGroup();

    /**
     * 初始化客户端
     * @param host:
     * @param port:
     * @return: null
     * @author: fengt
     * @date: 2021/11/22 13:53
     **/
    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.init();
    }

    private void init() {
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
//                        ch.pipeline().addLast(retryconnectHandler);
//                        ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(0, 12, 0));
//                        ch.pipeline().addLast("ClientIdleStateHandler", new ClientIdleStateHandler());
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(ProtocolConst.PACKAGE_MAX_SIZE, 3, 4, 0, 0));
//                        ch.pipeline().addLast(new LoggingHandler());
                        ch.pipeline().addLast(new MyMessageEncoder());
                        ch.pipeline().addLast(new MyMessageDecoder());
                        ch.pipeline().addLast(connectHandler);
                        ch.pipeline().addLast(new ClientMessageHandler());
                    }
                });
    }

    /**
     * 连接服务器
     * @return: void
     * @author: fengt
     * @date: 2021/11/22 13:53
     **/
    public ChannelFuture connect() {
        synchronized (NettyClient.this) {
            final ChannelFuture f = bootstrap.connect(this.host, this.port);
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        channel = future.channel();
                    } else {
                        future.channel().pipeline().fireChannelInactive();
                    }
                }
            });
            return f;
        }
    }

    /**
     * 发送消息
     * @param msg:
     * @return: boolean
     * @author: fengt
     * @date: 2021/11/22 13:53
     **/
    public static boolean send(BinlogMessage msg) {
        if (channel == null || !channel.isActive()) {
            System.out.println("未连接服务器，发送失败");
            return false;
        }

        ChannelFuture f = channel.writeAndFlush(msg);
        return f.isSuccess();
    }

    public static void main(String[] args) throws InterruptedException {
        final ChannelFuture connect = new NettyClient("10.0.122.109", 8881).connect();
        if (connect.sync().isSuccess())
            send(BinlogMessage.StringMessage("测试自定义协议消息发送"));
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public void shutdown() {
        if (group != null) {
            group.shutdownGracefully();
        }
    }

}
