package com.eddie.netty.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 消息盒子发送对象
 *
 * @author eddie.lys
 * @since 2021/9/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocketMessageDO implements Serializable {
    /**
     * 目标用户ID
     */
    private List<String> userIds;
    /**
     * 消息盒子发送消息内容
     */
    private SocketPushMessageDO socketPushMessage;
}
