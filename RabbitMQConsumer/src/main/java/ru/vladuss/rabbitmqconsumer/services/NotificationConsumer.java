//package ru.vladuss.rabbitmqconsumer.services;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Service
//public class NotificationConsumer {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);
//
//    @RabbitListener
//    public void handleNotification(String message) {
//        LOGGER.info("Получено обновление товара: {}", message);
//        System.out.println("Получено обновление товара: {} " + message);
//    }
//}
