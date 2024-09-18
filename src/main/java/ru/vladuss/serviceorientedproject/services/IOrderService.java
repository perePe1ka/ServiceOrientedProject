package ru.vladuss.serviceorientedproject.services;

import org.springframework.stereotype.Service;
import ru.vladuss.serviceorientedproject.entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface IOrderService<String> {
    void addOrder(Order order);

    void deleteByUUID(UUID uuid);

    Optional<Order> findByUUID(UUID uuid);

    List<Order> findAll();


    void updateOrderStatus();


    void editOrder(Order order);
}
