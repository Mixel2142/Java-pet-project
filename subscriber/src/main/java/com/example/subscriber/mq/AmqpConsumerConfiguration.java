package com.example.subscriber.mq;

import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

import java.util.Collections;

@Configuration
public class AmqpConsumerConfiguration {

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Bean
    public DirectMessageListenerContainer messageListenerContainer() {
        var container = new DirectMessageListenerContainer();
        container.setConnectionFactory(myrabbitConnectionFactory());
        container.setQueueNames("notification.email.queue");
        container.setMessageListener(exampleListener());
        container.setIdleEventInterval(10000);
        container.setConsumerArguments(Collections.singletonMap("x-max-priority", Integer.valueOf(10)));
        return container;
    }

    @Bean
    public CachingConnectionFactory myrabbitConnectionFactory() {
        var connectionFactory = new CachingConnectionFactory(rabbitmqHost);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        return connectionFactory;
    }

    @Bean
    public MessageListener exampleListener() {
        return new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println(new String(message.getBody()));
            }
        };
    }
}
