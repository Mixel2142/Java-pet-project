package com.example.subscriber;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@EnableRabbit //нужно для активации обработки аннотаций @RabbitListener
@Component
public class RabbitMqListener {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "notification.email.queue",
                    autoDelete = "false",
                    arguments = @Argument(
                            name = "x-max-priority",
                            value = "10",
                            type = "I"
                    )
            ),
            exchange = @Exchange(
                    value = "notification-service.direct",
                    type = ExchangeTypes.DIRECT,
                    autoDelete = "false"
            ),
            key = "notification.email",
            arguments = @Argument(
                    name = "x-max-priority",
                    value = "10",
                    type = "I"
            )
    )
    )
    public void listener(String message) {
        System.out.println(message);
    }
}
