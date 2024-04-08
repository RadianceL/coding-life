package com.eddie;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.prefs.Preferences;

/**
 * websocket客户端处理程序
 */
@Slf4j
public class WebsocketClientHandler extends SimpleChannelInboundHandler<Object> {
    /**
     * netty提供的数据过程中的数据保证
     */
    private ChannelPromise handshakeFuture;

    private final WebSocketClientHandshaker webSocketClientHandshaker;

    public WebsocketClientHandler(WebSocketClientHandshaker webSocketClientHandshaker) {
        this.webSocketClientHandshaker = webSocketClientHandshaker;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!webSocketClientHandshaker.isHandshakeComplete()) {
            try {
                //连接建立成功，握手完成
                webSocketClientHandshaker.finishHandshake(ctx.channel(), (FullHttpResponse) msg);
                //连接完成
                handshakeFuture.setSuccess();
                log.info("websocket已经建立连接");
            }
            catch (WebSocketHandshakeException e) {
                log.info( "websocket连接失败!");
                handshakeFuture.setFailure(e);
            }
            return;
        }
        if (msg instanceof FullHttpResponse response) {
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }
        this.handleWebSocketFrame(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("123", cause);
    }


    /**
     * 当客户端主动链接服务端的链接后,调用此方法
     *
     * @param ctx ChannelHandlerContext
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        webSocketClientHandshaker.handshake(ctx.channel());
    }

    /**
     * ChannelHandler添加到实际上下文中准备处理事件,调用此方法
     *
     * @param ctx ChannelHandlerContext
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    /**
     * 处理文本帧请求
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, Object msg) {
        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame textFrame) {
            String context = textFrame.text();
            log.info("handleWebSocketFrame{}", "收到消息：" + context);
        } else if (frame instanceof CloseWebSocketFrame) {
            log.info("handleWebSocketFrame{}", "连接收到关闭帧");
            ctx.channel().close();
        }
    }


    /**
     * 事件触发后会调用此方法
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE.equals(evt)) {
            log.info("{} 握手完成！", ctx.channel().id().asShortText());
        }
        if (evt instanceof IdleStateEvent e) {
            if (e.state() == IdleState.WRITER_IDLE) {
                String content = "客户端写事件触发，向服务端发送心跳包";
                ByteBuf byteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
                ctx.channel().writeAndFlush(byteBuf);
            } else if (e.state() == IdleState.READER_IDLE) {
                String content = "客户端读事件触发，向服务端发送心跳包";
                ByteBuf byteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
                ctx.channel().writeAndFlush(byteBuf);
            } else if (e.state() == IdleState.ALL_IDLE) {
                String content = "客户端的读/写事件触发，向服务端发送心跳包";
                ByteBuf byteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
                ctx.channel().writeAndFlush(byteBuf);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }
}