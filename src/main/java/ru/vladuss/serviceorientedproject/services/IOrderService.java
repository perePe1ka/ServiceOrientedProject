package ru.vladuss.serviceorientedproject.services;

import org.springframework.stereotype.Service;
import ru.vladuss.serviceorientedproject.entity.Orders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface IOrderService<String> {
    void addOrder(Orders orders);

    void deleteByUUID(UUID uuid);

    Optional<Orders> findByUUID(UUID uuid);

    List<Orders> findAll();


    void updateOrderStatus();


    void editOrder(Orders orders);
}
