package com.hmdp.mq;

import cn.hutool.json.JSONUtil;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.service.IVoucherOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RocketMQMessageListener(
        topic = "voucher-topic",
        consumerGroup = "voucher-group"
)
public class MQReceiver implements RocketMQListener<String> { // 直接接收String类型

    @Autowired
    private IVoucherOrderService orderService;

    @Override
    public void onMessage(String json) {
        // 1. 解析JSON
        VoucherOrder order = JSONUtil.toBean(json, VoucherOrder.class);

        // 2. 处理订单
        orderService.createVoucherOrder(order);

        log.info("消费成功: {}", order.getId());
    }
}