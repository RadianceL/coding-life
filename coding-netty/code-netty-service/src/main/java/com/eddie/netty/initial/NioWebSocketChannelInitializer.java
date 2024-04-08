package com.eddie.netty.initial;

import com.eddie.netty.handler.NioWebSocketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.TimeUnit;


/**
 * NIO socket监听通道初始化
 *
 * @author eddie.lys
 * @since 2021/9/15
 */
public class NioWebSocketChannelInitializer  extends ChannelInitializer<SocketChannel> {
    private static final EventExecutorGroup EVENT_EXECUTOR_GROUP = new DefaultEventExecutorGroup(100);

    @Override
    protected void initChannel(SocketChannel ch) {
        // 日志
        ch.pipeline().addLast("logging", new LoggingHandler(LogLevel.INFO));
        // 解码器
        ch.pipeline().addLast("http-codec", new HttpServerCodec());//设置解码器
        // 聚合器
        ch.pipeline().addLast("aggregator", new HttpObjectAggregator(8192));
        // 大数据的分区传输
        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        // 心跳检测
        ch.pipeline().addLast("server-idle-handler", new IdleStateHandler(15, 0, 0, TimeUnit.SECONDS));
        // 自定义的业务handler
        ch.pipeline().addLast("handler", new NioWebSocketHandler());;
    }
}