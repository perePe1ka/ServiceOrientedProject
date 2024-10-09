package ru.vladuss.serviceorientedproject.services;

import org.springframework.stereotype.Service;
import ru.vladuss.serviceorientedproject.entity.Orders;

import java.util.List;
import java.util.Optional;

@Service
public interface IOrderService<T, U> {
    void addOrder(T orders);

    void deleteByUUID(U uuid);

    Optional<Orders> findByUUID(U uuid);

    List<Orders> findAll();


    void updateOrderStatus();


    void editOrder(T orders);
}
