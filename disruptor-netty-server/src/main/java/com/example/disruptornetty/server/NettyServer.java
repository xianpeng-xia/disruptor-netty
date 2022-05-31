package com.example.disruptornetty.server;


import com.example.disruptornetty.common.entity.codec.MarshallingCodecFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

    public NettyServer() {
        //1 创建两个工作线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 缓存区动态调配（自适应）
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    // 缓存区 池化操作
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast(MarshallingCodecFactory.buildMarshallingDecoder());
                            sc.pipeline().addLast(MarshallingCodecFactory.buildMarshallingEncoder());
                            sc.pipeline().addLast(new ServerHanler());
                        }
                    });

            // 2 绑定端口

            ChannelFuture sf = bootstrap.bind(8765).sync();
            log.info("Server Startup...");
            sf.channel().closeFuture();
        } catch (InterruptedException e) {
            log.error("bind error", e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            log.info("Server ShutDown...");
        }
    }
}
