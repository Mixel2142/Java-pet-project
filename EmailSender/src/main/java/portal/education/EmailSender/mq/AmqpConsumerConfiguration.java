package portal.education.EmailSender.mq;

import lombok.SneakyThrows;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import portal.education.EmailSender.service.EmailSenderService;

import java.util.Collections;

@Configuration
public class AmqpConsumerConfiguration {

//    @Autowired
//    private EmailSenderService sender;

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.port}")
    private Integer port;


    @Value("${spring.rabbitmq.template.queue}")
    private String queueName;

    @Value("${spring.rabbitmq.template.exchange}")
    private String directExchangeName;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey;

    @Bean
    public DirectMessageListenerContainer messageListenerContainer() {
        var container = new DirectMessageListenerContainer();
        container.setConnectionFactory(myrabbitConnectionFactory());
        container.setQueueNames(queueName);
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
    Queue queue() {
        return new Queue(queueName, false, false, false, Collections.singletonMap("x-max-priority", Integer.valueOf(10)));
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(directExchangeName, false, false, Collections.singletonMap("x-max-priority", Integer.valueOf(10)));
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }


    @Bean
    public MessageListener exampleListener() {

        return new MessageListener() {
            @SneakyThrows
            @Override
            public void onMessage(Message message) {

                Thread.sleep(100);
                String msg = new String(message.getBody()).concat("Thread id:"+Thread.currentThread().getId());
                System.out.println(msg);

            }
        };
    }
}
