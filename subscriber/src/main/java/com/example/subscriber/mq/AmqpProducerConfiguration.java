package com.example.subscriber.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class AmqpProducerConfiguration {

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.application.name}")
    public String appName;

    @Autowired
    private ApplicationContext context;
//    @Bean
//    public ConnectionFactory rabbitConnectionFactory() {
//        var connectionFactory = new CachingConnectionFactory(rabbitmqHost);
//        connectionFactory.setPort(port);
//        connectionFactory.setUsername(userName);
//        connectionFactory.setPassword(password);
//        return connectionFactory;
//    }

    @Bean
    Queue queue() {
        return new Queue("notification.email.queue", false,false,false, Collections.singletonMap("x-max-priority",Integer.valueOf(10)));
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(appName + ".direct",false,false,Collections.singletonMap("x-max-priority",Integer.valueOf(10)));
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("notification.email");
    }
}
