package com.itheima.netty_chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description:
 * @author: lyw
 * @time: 2020/3/14 17:02
 */
public class WebSocketNettyServer {
    public static void main(String[] args) {
        NioEventLoopGroup mainGro = new NioEventLoopGroup();//主线程池
        NioEventLoopGroup subGro = new NioEventLoopGroup();//从线程池

//        创建netty服务器启动对象
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(mainGro,subGro)//执行线程池
                    .channel(NioServerSocketChannel.class)
                    //制定通道初始化器用来加载当channel接收到事件后，如何进行业务处理
                    .childHandler(new WebSocketChannelInitializer());//制定处理器

            //绑定端口
            ChannelFuture future = bootstrap.bind(9090).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅关闭线程池
            mainGro.shutdownGracefully();
            subGro.shutdownGracefully();
        }


    }
}
