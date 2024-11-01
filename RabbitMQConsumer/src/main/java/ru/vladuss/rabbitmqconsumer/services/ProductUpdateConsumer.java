package ru.vladuss.rabbitmqconsumer.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductUpdateConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ProductUpdateConsumer.class);

    @RabbitListener(queues = "product-update-queue")
    public void receiveProductUpdate(String message) {
        logger.info("Получено обновление товара: {}", message);

    }
}
