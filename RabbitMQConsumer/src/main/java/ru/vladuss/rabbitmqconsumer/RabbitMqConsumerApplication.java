package ru.vladuss.rabbitmqconsumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitMqConsumerApplication {

    public static final String OrderStatusQueueName = "order-status-queue";
    @Bean
    public Queue notificationQueue() {
        return new Queue(OrderStatusQueueName, false);
    }


    @RabbitListener(queues = OrderStatusQueueName)
    public void ListenNotification(String message) {
        System.out.println("Прочитано с очереди " + OrderStatusQueueName + " сообщение " + " " + message);
    }


//    static final String inventoryQueueName = "inventory-queue";
//
//    static final String notificationQueueName = "notification-queue";

//    @Bean
//    public Queue inventoryQueue() {
//        return new Queue(inventoryQueueName, false);
//    }

//    @Bean
//    public Queue notificationQueue() {
//        return new Queue(notificationQueueName, false);
//    }

//    @RabbitListener(queues = inventoryQueueName)
//    public void Listen(String message) {
//        System.out.println("Прочитано с очереди " + inventoryQueueName + " сообщение " + " " + message);
//    }

//    @RabbitListener(queues = notificationQueueName)
//    public void ListenNotification(String message) {
//        System.out.println("Прочитано с очереди " + notificationQueueName + " сообщение " + " " + message);
//    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqConsumerApplication.class, args);
        System.setProperty("server.port", "8082");
    }

}
