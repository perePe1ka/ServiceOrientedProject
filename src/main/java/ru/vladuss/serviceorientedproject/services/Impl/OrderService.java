package ru.vladuss.serviceorientedproject.services.Impl;

import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vladuss.serviceorientedproject.constants.Status;
import ru.vladuss.serviceorientedproject.entity.Order;
import ru.vladuss.serviceorientedproject.entity.Product;
import ru.vladuss.serviceorientedproject.repositories.IOrderRepository;
import ru.vladuss.serviceorientedproject.repositories.IProductRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService  {

    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;

    @Autowired
    public OrderService(IOrderRepository orderRepository, IProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addOrder(Order order) {
        boolean isProductInStock = order.getProducts().stream()
                .allMatch(product -> {
                    Product product1 = productRepository.findById(product.getUuid()).orElseThrow(() ->
                            new IllegalArgumentException("Не нашли продукт:" + product.getName()));

                    return product1.getStockQuantity() >= 1;
                });

        if (isProductInStock) {
            order.getProducts().forEach(product -> {
                Product product1 = productRepository.findById(product.getUuid()).get();
                int newStock = product1.getStockQuantity() - 1;
                product1.setStockQuantity(newStock);

                if (newStock == 0) {
                    product1.setInStock(false);
                }
                productRepository.saveAndFlush(product1);
            });

            order.setStatus(Status.ORDERED);
            orderRepository.saveAndFlush(order);
        } else {
            System.out.println("logi");
        }
    }


    public void deleteByUUID(UUID uuid) {
        orderRepository.deleteById(uuid);
    }


    public Optional<Order> findByUUID(UUID uuid) {
        return orderRepository.findById(uuid);
    }


    public List<Order> findAll() {
        return null;
    }


    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void updateOrderStatus() {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            Status currStatus = order.getStatus();
            switch (currStatus) {
                case ORDERED:
                    order.setStatus(Status.CONFIRMED);
                    break;
                case CONFIRMED:
                    order.setStatus(Status.ASSEMBLING);
                    break;
                case ASSEMBLING:
                    order.setStatus(Status.PACKED);
                    break;
                case PACKED:
                    order.setStatus(Status.SHIPPED);
                    break;
                case SHIPPED:
                    order.setStatus(Status.IN_TRANSIT);
                    break;
                case IN_TRANSIT:
                    order.setStatus(Status.AWAITING_PICKUP);
                    break;
                case AWAITING_PICKUP:
                    order.setStatus(Status.DELIVERED);
                    break;
                default:
                    System.out.println("logi dostavlen ili otmena");
                    break;
            }
            orderRepository.saveAndFlush(order);
        }
    }

    @Transactional
    public void editOrder(Order order) {
        Optional<Order> existingOrder = orderRepository.findById(order.getUuid());

        if (existingOrder.isPresent()) {
            Order currOrder = existingOrder.get();
            currOrder.setCustomerName(order.getCustomerName());
            currOrder.setCustomerAddress(order.getCustomerAddress());
            currOrder.setOrderDate(order.getOrderDate());
            currOrder.setOrderCost(order.getOrderCost());

            orderRepository.saveAndFlush(currOrder);
        } else {
            System.out.println("logi");
        }
    }
}
