package com.eddie.netty.holder;

import com.alibaba.fastjson2.JSON;
import com.eddie.netty.handler.keeper.ChannelSupervise;
import com.eddie.netty.message.SocketMessageDO;
import com.eddie.netty.message.SocketPushMessageDO;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 提示消息持有者
 *
 * @author eddie.lys
 * @since 2021/9/15
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SocketMessagePushHolder {

    /**
     * 消息推送服务
     * @param socketMessage     消息推送内容
     * @return                  是否推送成功
     */
    public boolean messagePush(SocketMessageDO socketMessage) {
        List<String> userIds = socketMessage.getUserIds();
        List<Channel> channels = new ArrayList<>();
        userIds.forEach(userId -> {
            Channel channel = ChannelSupervise.findChannelByUserId(userId);
            if (Objects.nonNull(channel)) {
                channels.add(channel);
            }
        });
        SocketPushMessageDO socketPushMessage = socketMessage.getSocketPushMessage();
        String socketPushMessageJson = JSON.toJSONString(socketPushMessage);
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(socketPushMessageJson);
        for (Channel channel : channels) {
            try {
                ChannelFuture channelFuture = channel.writeAndFlush(textWebSocketFrame).sync();
                channelFuture.addListener((ChannelFutureListener) channelFutureTask
                        -> log.info("push socket message success, message: {}", socketPushMessageJson)
                );
            } catch (InterruptedException e) {
                log.error("push socket message failed", e);
            }
        }
        return true;
    }

}
