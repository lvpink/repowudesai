package com.itheima.netty_chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: lyw
 * @time: 2020/3/19 16:45
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //保存所有客户端连接
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //时间格式化器
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM");
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) throws Exception {
        //接收到信息后，处理的方法
        String message = msg.text();
        System.out.println("服务器接收到的消息："+message);
        
        //遍历
        for (Channel client :clients) {
            //将消息发送到客户端
            client.writeAndFlush(new TextWebSocketFrame(sdf.format(new Date())+" : "+message));
        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //将新通道加入到clients里
        clients.add(ctx.channel());
    }
}
