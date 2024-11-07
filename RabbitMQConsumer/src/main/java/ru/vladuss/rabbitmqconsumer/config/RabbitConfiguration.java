package ru.vladuss.rabbitmqconsumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange; // Или DirectExchange, если предпочитаете
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    public static final String ORDER_STATUS_QUEUE = "order-status-queue";
    public static final String ORDER_STATUS_EXCHANGE = "order-status-exchange";
    public static final String ORDER_STATUS_ROUTING_KEY = "order.status.update";

    @Bean
    public Queue orderStatusQueue() {
        return new Queue(ORDER_STATUS_QUEUE, false);
    }

    @Bean
    public Exchange orderStatusExchange() {
        return new TopicExchange(ORDER_STATUS_EXCHANGE);
    }

    @Bean
    public Binding orderStatusBinding(Queue orderStatusQueue, Exchange orderStatusExchange) {
        return BindingBuilder.bind(orderStatusQueue)
                .to(orderStatusExchange)
                .with(ORDER_STATUS_ROUTING_KEY)
                .noargs();
    }
}
