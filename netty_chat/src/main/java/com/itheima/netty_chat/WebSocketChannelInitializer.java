package com.itheima.netty_chat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * @description:通道初始化器，用来加载通道处理器
 * @author: lyw
 * @time: 2020/3/19 16:27
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    //初始化通道
    protected void initChannel(SocketChannel ch) throws Exception {
        //获取管道，将一个一个的channelHandler 加载到管道中
        ChannelPipeline pipeline = ch.pipeline();

        //添加编解码器
        pipeline.addLast(new HttpServerCodec());
        //添加大数据流支持
        pipeline.addLast(new ChunkedWriteHandler());
        //添加聚合器
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        //制定接收的路由
        //必须以“/ws”结尾的才能被接收
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //制定自定义handler
        pipeline.addLast(new ChatHandler());


    }
}
