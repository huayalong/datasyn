package com.ruoyi.datanetty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.datanetty.protocal.MyMessageDecoder;
import com.ruoyi.datanetty.protocal.MyMessageEncoder;
import com.ruoyi.datanetty.protocal.ProtocolConst;
import com.ruoyi.datanetty.server.config.ServerCfg;
import com.ruoyi.datanetty.server.handler.ConnectHandler;
import com.ruoyi.datanetty.server.handler.ServerIdleStateHandler;
import com.ruoyi.datanetty.server.handler.ServerMessageHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 
 * NettyServer 负责接收来自客户端连接 推送来自binlog的消息
 * @author admin
 *
 */
public class NettyServer {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void start() {
    	
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)

                    //实例化ServerSocketChannel
                    .channel(NioServerSocketChannel.class)

                    //设置ServerSocketChannel的TCP参数
                    .option(ChannelOption.SO_BACKLOG, 1024)

                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // http服务端编解码规则
                            ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(ServerCfg.HEART_READ_TIME, 0, 0));
                            ch.pipeline().addLast("ServerIdleStateHandler", new ServerIdleStateHandler());
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(ProtocolConst.PACKAGE_MAX_SIZE,3,4,0,0));
                            //ch.pipeline().addLast(new LoggingHandler());
                            ch.pipeline().addLast(new MyMessageEncoder());
                            ch.pipeline().addLast(new MyMessageDecoder());
                            ch.pipeline().addLast(new ConnectHandler());
                            ch.pipeline().addLast(new ServerMessageHandler());
                        }
                    });
            
            //绑定监听端口，调用sync同步阻塞方法等待绑定操作完
            ChannelFuture future = b.bind(this.port).sync();
            if(future.isSuccess()){
            	//成功绑定到端口之后,给channel增加一个管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程。
            	future.channel().closeFuture().sync();
            	logger.debug("NettyServer has been started, listen on: {}",this.port);
            };
            
            
        } catch (InterruptedException e) {
        	e.printStackTrace();
        	logger.error("netty service failed to start on " + port + " port", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyServer(8881).start();
    }
}
