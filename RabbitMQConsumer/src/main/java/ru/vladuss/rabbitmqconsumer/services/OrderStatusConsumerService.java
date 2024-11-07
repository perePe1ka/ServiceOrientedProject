package ru.vladuss.rabbitmqconsumer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vladuss.rabbitmqconsumer.dtos.ConsumerOrdersStatusUpdateDto;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static ru.vladuss.rabbitmqconsumer.RabbitMqConsumerApplication.OrderStatusQueueName;

@Service
public class OrderStatusConsumerService {
    private final Map<UUID, Integer> orderTimeTracker = new HashMap<>();
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderStatusConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = OrderStatusQueueName)
    public void receiveOrderStatusUpdate(String message) {
        try {
            ConsumerOrdersStatusUpdateDto updateDto = objectMapper.readValue(message, ConsumerOrdersStatusUpdateDto.class);
            UUID orderId = UUID.fromString(updateDto.getUuid());

            orderTimeTracker.put(orderId, orderTimeTracker.getOrDefault(orderId, 0) + updateDto.getDelay());
            if (orderTimeTracker.get(orderId) > 20 && updateDto.getStatus().equals("DELIVERED")) {
                applyDiscount(updateDto.getOrderCost(), orderId);
                orderTimeTracker.remove(orderId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyDiscount(long orderCost, UUID orderId) {
        long discountedCost = (long) (orderCost * 0.9);
        System.out.println("Скидка 10% применена к заказу с ID: " + orderId + " теперь его стоимость " + discountedCost);

    }
}
