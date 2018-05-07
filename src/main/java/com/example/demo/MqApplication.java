package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
@RestController
public class MqApplication {

	@Resource(name = "cmdProducer")
	private DefaultMQProducer cmdProducer;

	public static void main(String[] args) {
		SpringApplication.run(MqApplication.class, args);
	}

	@RequestMapping("/hello")
	public String hello() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for(int i=0;i<=10;i++){
            String a = i+"baby baby baby...."+i;
            log.info("-------------{}",i);
            cmdProducer.send(new Message("demo_mq",i+"send"+i,i+"key"+i,a.getBytes()));
            log.info("生产了一个消息，topic：【{}】tag：【{}】 key：【{}】 body：【{}】","demo_mq",i+"send"+i,i+"key"+i,a);
            TimeUnit.MILLISECONDS.sleep(3000);
        }
		return "this is Marketing mobile phone!";
	}
}
