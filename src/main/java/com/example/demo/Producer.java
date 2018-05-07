package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Created by Administrator on 2018/3/22 0022.
 */
@Configuration
@Slf4j
public class Producer {

    /**
     * 初始化向rocketmq下发指令的生产者
     */
    @Bean(destroyMethod = "shutdown")
    @Lazy
    @ConditionalOnMissingBean(value = MQProducer.class, name = "cmdProducer")
    public DefaultMQProducer cmdProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("demo_mq_group");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setVipChannelEnabled(false);
        //producer.setRetryTimesWhenSendFailed(3);//失败的重新发送

        producer.start();
        log.info("RocketMq cmdProducer Started.");
        return producer;
    }
}
