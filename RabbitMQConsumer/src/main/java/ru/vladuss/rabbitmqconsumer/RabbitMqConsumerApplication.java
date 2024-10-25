package ru.vladuss.rabbitmqconsumer;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitMqConsumerApplication {

    static final String queueName = "firstQueue";

    @Bean
    public Queue myQueue() {
        return new Queue(queueName, false);
    }

    @RabbitListener(queues = queueName)
    public void Listen(String message) {
        System.out.println("Прочитано с очереди " + queueName + " сообщение " + " " + message);
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqConsumerApplication.class, args);
        System.setProperty("server.port", "8082");
    }

}
