package ru.vladuss.serviceorientedproject.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vladuss.serviceorientedproject.entity.Order;
import ru.vladuss.serviceorientedproject.repositories.IOrderRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService  {

    private IOrderRepository orderRepository;

    @Autowired
    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public void addOrder(Order order) {

    }


    public void deleteByUUID(String uuid) {

    }


    public Optional<Order> findByUUID(UUID uuid) {
        return Optional.empty();
    }


    public List<Order> findAll() {
        return null;
    }


    public void editOrder(Order order) {

    }


    public Order getAll(String order) {
        return null;
    }
}
