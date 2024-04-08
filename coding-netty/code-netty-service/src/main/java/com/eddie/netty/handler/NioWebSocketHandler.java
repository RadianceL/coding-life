package com.eddie.netty.handler;

import com.eddie.netty.handler.keeper.ChannelSupervise;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;

/**
 * 自定义业务渠道处理器
 *
 * @author eddie.lys
 * @since 2021/9/15
 */
@Slf4j
public class NioWebSocketHandler extends SimpleChannelInboundHandler<Object> {

    public static ChannelGroup cg = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * CONNECTION UPGRADE WebSocket
     */
    private WebSocketServerHandshaker webSocketServerHandshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("Channel short ID: [{}]，rec：{}" , ctx.channel().id(), msg);
        if (msg instanceof FullHttpRequest){
            // 以http方式升级WebSocket连接，该方法每次连接应该仅执行一次
            log.info("Channel short ID: [{}]，upgrade WebSocket" , ctx.channel().id());
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }else if (msg instanceof  WebSocketFrame){
            // 处理websocket客户端的消息
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("client add to server: {}", ctx.channel().id());
        ChannelSupervise.addChannel(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("client disconnect server：{}", ctx.channel().id());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("client transmission error，disconnected", cause);
//        if(ctx.channel().isActive()) {
//            ctx.close();
//        }
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame){
        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            webSocketServerHandshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            ChannelSupervise.removeChannel(ctx.channel());
            return;
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            log.debug("本例程仅支持文本消息，不支持二进制消息");
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }

        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        TextWebSocketFrame tws = new TextWebSocketFrame(new Date().toString() + ctx.channel().id() + request);
        ctx.channel().writeAndFlush(tws);
    }

    /**
     * 唯一的一次http请求，用于创建websocket
     * @param ctx       渠道上下文
     * @param req       Http请求
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        //要求Upgrade为websocket，过滤掉get/Post
        if (!req.decoderResult().isSuccess()
                || (!"websocket".equals(req.headers().get("Upgrade")))) {
            //若不是websocket方式，则创建BAD_REQUEST的req，返回给客户端
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(req.protocolVersion(), HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory =
                new WebSocketServerHandshakerFactory("", null, false);
        webSocketServerHandshaker = wsFactory.newHandshaker(req);
        if (webSocketServerHandshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            String userId = getUrlKey(req.uri(), "channel-auth-user");
            if (StringUtils.isBlank(userId)) {
                ctx.channel().writeAndFlush("渠道认证用户信息不能为空");
                throw new RuntimeException("渠道认证用户信息不能为空");
            }
            ChannelSupervise.authChannel(userId, ctx.channel());
            webSocketServerHandshaker.handshake(ctx.channel(), req);
        }
    }

    /**
     * 拒绝不合法的请求，并返回错误信息
     * @param ctx       渠道上下文
     * @param req       请求
     * @param res       返回
     */
    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), StandardCharsets.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        // 如果是非Keep-Alive，关闭连接
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }


    public static String getUrlKey(String url, String key) {
        String[] arrSplit = null;
        //每个键值为一组
        url = truncateUrlPage(url);
        arrSplit = url.split("&");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual;
            arrSplitEqual = strSplit.split("=");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                if(key.equals(arrSplitEqual[0])){
                    return arrSplitEqual[1];
                }
            }
        }
        return null;
    }

    private static String truncateUrlPage(String url) {
        String strAllParam = null;
        String[] arrSplit = null;
        url = url.trim();
        arrSplit = url.split("[?]");
        if (url.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }

    /**
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent e) {
            if (e.state() == IdleState.READER_IDLE) {
                String content = "服务端读事件触发，向客户端发送心跳包";
                TextWebSocketFrame tws = new TextWebSocketFrame(new Date() + ":" + content);
                ctx.channel().writeAndFlush(tws);
            } else if (e.state() == IdleState.WRITER_IDLE) {
                String content = "服务端写事件触发，向客户端发送心跳包";
                TextWebSocketFrame tws = new TextWebSocketFrame(new Date() + ":" + content);
                // 发送心跳包
                ctx.channel().writeAndFlush(tws);
            } else if (e.state() == IdleState.ALL_IDLE) {
                String content = "服务端的读/写事件触发，向客户端发送心跳包";
                TextWebSocketFrame tws = new TextWebSocketFrame(new Date() + ":" + content);
                // 发送心跳包
                ctx.channel().writeAndFlush(tws);
            }
        }
    }

}
