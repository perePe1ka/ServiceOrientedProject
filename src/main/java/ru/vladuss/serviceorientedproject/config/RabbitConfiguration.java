package ru.vladuss.serviceorientedproject.config;


import org.springframework.amqp.core.*;
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

//    private final String queueName = "product-update-queue";
//
//    private final String exchangeName = "testExchange";

    /**
     * Очередь для проверки состояния
     */

//    @Bean
//    public Queue inventoryQueue() {
//        return new Queue("inventory-queue", false);
//    }
//
//    @Bean
//    Exchange inventoryExchange() {
//        return new TopicExchange("inventoryExchange", false, false);
//    }
//
//    @Bean
//    Binding inventoryBinding(Queue inventoryQueue, Exchange inventoryExchange) {
//        return BindingBuilder.bind(inventoryQueue).to(inventoryExchange).with("inventory.key").noargs();
//    }


    /**
     * Очередь для уведомления
     */

//    @Bean
//    public Queue notificationQueue() {
//        return new Queue("notification-queue", false);
//    }
//
//    @Bean
//    Exchange notificationExchange() {
//        return new TopicExchange("notificationExchange", false, false);
//    }
//
//    @Bean
//    Binding notificationBinding(Queue notificationQueue, Exchange notificationExchange) {
//        return BindingBuilder.bind(notificationQueue).to(notificationExchange).with("notification.key").noargs();
//    }

    /**
     * Старое ненужное
     */

//    @Bean
//    public Queue myQueue() {
//        return new Queue(queueName, false);
//    }
//
//    @Bean
//    Exchange exchange() {
//        return new TopicExchange(exchangeName, false, false);
//    }
//
//    @Bean
//    Binding binding(Queue myQueue, Exchange exchange) {
//        return BindingBuilder.bind(myQueue).to(exchange).with("my.key").noargs();
//    }

    /**
     *  С заказами
     */

//    @Bean
//    public Queue orderStatusQueue() {
//        return new Queue("order-status-queue", false);
//    }
//
//    @Bean
//    public TopicExchange orderExchange() {
//        return new TopicExchange("orderExchange");
//    }
//
//    @Bean
//    public Binding orderStatusBinding(Queue orderStatusQueue, TopicExchange orderExchange) {
//        return BindingBuilder.bind(orderStatusQueue).to(orderExchange).with("order.status");
//    }
//


}
