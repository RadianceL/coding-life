package com.eddie.client;

import com.eddie.WebsocketClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * @author eddie.lys
 * @since 2024/4/7
 */
public class WebsocketClient {

    private final Bootstrap bootstrap;

    private Channel channel;

    private final WebsocketClientHandler websocketClientHandler;

    public WebsocketClient(String url) throws URISyntaxException {
        final URI webSocketURL = new URI(url);
        WebSocketClientHandshaker webSocketClientHandshaker = WebSocketClientHandshakerFactory.newHandshaker(webSocketURL, WebSocketVersion.V13, null, true, new DefaultHttpHeaders());
        websocketClientHandler = new WebsocketClientHandler(webSocketClientHandshaker);
        EventLoopGroup group = new NioEventLoopGroup(); // 创建一个NioEventLoopGroup对象，它负责处理I/O操作的多线程事件循环
        // 开始try-catch块，用于捕获可能的异常
        bootstrap = new Bootstrap(); // 创建一个Bootstrap对象，它是Netty应用程序的入口点
        bootstrap.group(group) // 设置EventLoopGroup，用于处理I/O操作
                .channel(NioSocketChannel.class) // 指定用于通信的Channel类型
                .handler(new ChannelInitializer<SocketChannel>() { // 添加一个ChannelInitializer，用于初始化新连接的Channel
                    @Override // 覆盖ChannelInitializer中的初始化方法
                    protected void initChannel(SocketChannel ch) throws Exception { // 初始化Channel
                        //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                        ch.pipeline().addLast(new HttpClientCodec());
                        ch.pipeline().addLast(new HttpObjectAggregator(8192));
                        ch.pipeline().addLast(WebSocketClientCompressionHandler.INSTANCE);
//                           // 添加IdleStateHandler实例，5秒内未写入到Channel的数据时，触发 WRITER_IDLE 事件
//                        ch.pipeline().addLast(new IdleStateHandler(0, 10, 0, TimeUnit.SECONDS));
                        ch.pipeline().addLast(websocketClientHandler);
                    }
                });

    }

    public void doConnect() throws InterruptedException {
        channel = bootstrap.connect("127.0.0.1", 19081).sync().channel();
        websocketClientHandler.handshakeFuture().sync();
    }

    public void sendMessage(String message) {
        channel.writeAndFlush(new TextWebSocketFrame(message));
    }
}
