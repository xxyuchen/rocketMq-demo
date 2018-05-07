package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/3/22 0022.
 */
@Configuration
@Slf4j
public class RocketConsumer {

    /**
     * 初始化rocketmq消息监听方式的消费者
     */
    @Bean(destroyMethod = "shutdown", name = "cmdConsumer")
    @Lazy(false)
    public MQPushConsumer cmdConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("demo_mq_consumer_group");
        consumer.setNamesrvAddr("127.0.0.1:9876");

        //consumer.setConsumeMessageBatchMaxSize(1);//设置批量消费，以提升消费吞吐量，默认是1

        consumer.subscribe("demo_mq", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt msg = list.get(0);
                log.info("消费掉一个消息，topic:【{}】,tag：【{}】,key：【{}】,body：【{}】",msg.getTopic(),msg.getTags(),msg.getKeys(),msg.getBody().toString());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        log.info("cmdConsumer started....");
        return consumer;

    }
}
