package com.hmdp.mq;

import cn.hutool.json.JSONUtil;
import com.hmdp.entity.VoucherOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Slf4j
public class MQSender {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送秒杀订单消息（JSON格式）
     */
    public void sendSeckillMessage(VoucherOrder order) {
        String json = JSONUtil.toJsonStr(order); // 使用Hutool工具类转JSON
        rocketMQTemplate.convertAndSend("voucher-topic", json);
        log.info("发送消息成功: {}", json);
    }
}