package ru.vladuss.serviceorientedproject.services.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vladuss.serviceorientedproject.services.dto.OrdersStatusUpdateDto;

import static ru.vladuss.serviceorientedproject.config.RabbitConfiguration.ORDER_STATUS_EXCHANGE;
import static ru.vladuss.serviceorientedproject.config.RabbitConfiguration.ORDER_STATUS_ROUTING_KEY;

@Service
public class SenderService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public SenderService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Для продуктов инвентарь и уведомления
     */

    public void sendInventoryUpdate(String productUUID, int newQuantity, boolean isAvailable) {
        String message = String.format("Товар ID: %s, Количество: %d, В наличии: %b", productUUID, newQuantity, isAvailable);
        rabbitTemplate.convertAndSend("inventoryExchange", "inventory.key", message);
    }

    public void sendNotification(String productUUID) {
        String message = String.format("Товар ID: %s снова доступен!", productUUID);
        rabbitTemplate.convertAndSend("notificationExchange", "notification.key", message);
    }

    /**
     * Для заказа статусы заказа
     */

    public void sendUpdateOfOrderStatus(OrdersStatusUpdateDto update) {
        try {
            String message = objectMapper.writeValueAsString(update);
            rabbitTemplate.convertAndSend(ORDER_STATUS_EXCHANGE, ORDER_STATUS_ROUTING_KEY, message);
            System.out.println("Отправлено сообщение о статусе заказа: " + message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

