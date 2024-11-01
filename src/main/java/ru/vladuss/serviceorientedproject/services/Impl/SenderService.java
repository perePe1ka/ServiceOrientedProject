package ru.vladuss.serviceorientedproject.services.Impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SenderService {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public SenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendProduct(String productUUID, int newQuantity, boolean isAvailable) {
        String message = String.format("Товар ID: %s, Количество: %d, В наличии: %b", productUUID, newQuantity, isAvailable);
        rabbitTemplate.convertAndSend("testExchange", "my.key", message);
    }
}

