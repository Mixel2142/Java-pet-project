package portal.education.NotificationResolver.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${spring.rabbitmq.template.queue-email}")
    private String queueNameEmail;

    @Value("${spring.rabbitmq.template.exchange}")
    private String directExchangeName;

    @Value("${spring.rabbitmq.template.routing-key-email}")
    private String routingKeyEmail;

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
    Queue queueEmail() {
        return new Queue(queueNameEmail, false, false, false, Collections.singletonMap("x-max-priority", Integer.valueOf(10)));
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(directExchangeName, false, false, Collections.singletonMap("x-max-priority", Integer.valueOf(10)));
    }

    @Bean
    Binding bindingEmail(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKeyEmail);
    }

}
