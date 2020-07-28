package portal.education.NotificationResolver;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@EnableScheduling
public class Runner {

    @Value("${spring.rabbitmq.template.exchange}")
    private String directExchangeName;

    @Autowired
    public CachingConnectionFactory rabbitConnectionFactory;

    @Value("${spring.rabbitmq.template.routing-key-email}")
    private String routingKeyEmail;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(CachingConnectionFactory myrabbitConnectionFactory) {
        this.rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
    }


    @Scheduled(fixedRate = 10000,initialDelay = 5000)
    public void run() {

        Random rnd = new Random();
        var messageProperties = MessagePropertiesBuilder
                .newInstance()
                .setReceivedRoutingKey(routingKeyEmail)
                .setReceivedExchange(directExchangeName);

        String body = "Msg num:%d pri:%d";
        System.out.println("Sending message...");
        for (int i = 0; i < 100; i++) {
            Integer priority = rnd.nextInt(10);
            String msg = String.format(body, i, priority);

            rabbitTemplate.send(directExchangeName, routingKeyEmail, new Message(msg.getBytes(), messageProperties.setPriority(priority).build()));
        }
    }

}
