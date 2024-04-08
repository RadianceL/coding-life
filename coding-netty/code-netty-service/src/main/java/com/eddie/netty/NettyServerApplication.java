package com.eddie.netty;

import com.eddie.netty.initial.NioWebSocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eddie.lys
 * @since 2024/4/5
 */
@Slf4j
public class NettyServerApplication {

    public static void main(String[] args) {
        //用于接收客户端的链接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 创建worker线程组，用于处理连接上的I/O操作，或者执行系统Task、定时任务Task等，含有子线程NioEventGroup个数为CPU核数大小的2倍
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 一个负责tcp链路的建立和断开，比较轻，但很重要
            //一个负责建立好的tcp链路上的数据读取和写入，比较重
            // 如果前后两者一起混用，但后者太繁忙的话，会导致建链等太久，导致操作系统的链接队列变满，连锁反应很大
            bootstrap.group(boss, work);
            // 设置服务端Channel类型为NioServerSocketChannel作为通道实现，指明使用NIO进行网络通讯
            bootstrap.channel(NioServerSocketChannel.class);
            //临时存放已完成三次握手的请求的队列的最大长度
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
//            //禁用nagle算法，不等待，立即发送
//            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
//            //当没有数据包过来时超过一定时间主动发送一个ack探测包
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
//            //允许共用端口
//            bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
            // 维护了一个双向链表，入站出站两个方向处理请求（与spring security类似）
            bootstrap.childHandler(new NioWebSocketChannelInitializer());
            // 绑定端口并启动服务器，bind方法是异步的，sync方法是等待异步操作执行完成，返回ChannelFuture异步对象
            ChannelFuture channelFuture = bootstrap.bind(19081).sync();
            // 等待服务器关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("startup error", e);
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
