package com.example.subscriber;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Runner {

    @Autowired
    public DirectExchange directExchange;

    @Autowired
    public CachingConnectionFactory rabbitConnectionFactory;

    @Autowired
    private ApplicationContext context;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(CachingConnectionFactory myrabbitConnectionFactory) {
        this.rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
    }


    @Scheduled(fixedRate = 2000)
    public void run() {

        Random rnd = new Random();
        var messageProperties = MessagePropertiesBuilder
                .newInstance()
                .setReceivedRoutingKey("notification.email")
                .setReceivedExchange(directExchange.getName());

        String body = "It's my first message) number:%d priority:%d";
        System.out.println("Sending message...");
        for (int i = 0; i < 100; i++) {
            Integer priority = rnd.nextInt(10);
            String msg = String.format(body, i, priority);

            rabbitTemplate.send(directExchange.getName(), "notification.email", new Message(msg.getBytes(), messageProperties.setPriority(priority).build()));
        }
    }

}
