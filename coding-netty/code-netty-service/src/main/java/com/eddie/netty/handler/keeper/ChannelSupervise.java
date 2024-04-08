package com.eddie.netty.handler.keeper;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 渠道存活
 *
 * @author eddie.lys
 * @since 2021/9/15
 */
public class ChannelSupervise {

    /**
     * netty alive group
     */
    private static final ChannelGroup GLOBAL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * channel map<ID, ChannelId>
     */
    private static final Map<String, ChannelId> CHANNEL_MAP = new ConcurrentHashMap<>(32);
    /**
     * channel map<USER_ID, ID>
     */
    private static final Map<String, String> USER_ID_CHANNEL_MAP = new ConcurrentHashMap<>(32);

    /**
     * add unauthorized channel
     * @param channel       渠道
     */
    public static void addChannel(Channel channel) {
        GLOBAL_GROUP.add(channel);
        CHANNEL_MAP.put(channel.id().asShortText(), channel.id());
    }

    /**
     * channel disconnect then remove channel
     * @param channel       渠道
     */
    public static void removeChannel(Channel channel) {
        GLOBAL_GROUP.remove(channel);
        String channelShortId = channel.id().asShortText();
        CHANNEL_MAP.remove(channelShortId);
        Set<String> userIds = USER_ID_CHANNEL_MAP.keySet();
        for (String userId : userIds) {
            if (channelShortId.equals(USER_ID_CHANNEL_MAP.get(userId))) {
                USER_ID_CHANNEL_MAP.remove(userId);
                break;
            }
        }
    }

    /**
     * auth channel
     * @param userId        userId
     * @param channel       渠道
     */
    public static void authChannel(String userId, Channel channel) {
        USER_ID_CHANNEL_MAP.put(userId, channel.id().asShortText());
    }

    /**
     * 通过渠道ID查询
     * @param id    channel id
     * @return      Channel
     */
    public static Channel findChannelById(String id) {
        return GLOBAL_GROUP.find(CHANNEL_MAP.get(id));
    }

    /**
     * 通过渠道ID查询
     * @param userId    channel id
     * @return          Channel
     */
    public static Channel findChannelByUserId(String userId) {
        String channelId = USER_ID_CHANNEL_MAP.get(userId);
        if (StringUtils.isBlank(channelId)) {
            return null;
        }
        return GLOBAL_GROUP.find(CHANNEL_MAP.get(channelId));
    }

    public static void sendMessageToAllByGroup(TextWebSocketFrame tws) {
        GLOBAL_GROUP.writeAndFlush(tws);
    }
}
