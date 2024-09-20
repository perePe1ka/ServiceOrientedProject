package ru.vladuss.serviceorientedproject.services.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.vladuss.serviceorientedproject.constants.Status;
import ru.vladuss.serviceorientedproject.entity.Orders;
import ru.vladuss.serviceorientedproject.entity.Product;
import ru.vladuss.serviceorientedproject.repositories.IOrderRepository;
import ru.vladuss.serviceorientedproject.repositories.IProductRepository;
import ru.vladuss.serviceorientedproject.services.IOrderService;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService implements IOrderService<String> {

    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;

    @Autowired
    public OrderService(IOrderRepository orderRepository, IProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void addOrder(Orders orders) {
        boolean isProductInStock = orders.getProducts().stream()
                .allMatch(product -> {
                    Product product1 = productRepository.findById(product.getUuid()).orElseThrow(() ->
                            new IllegalArgumentException("Не нашли продукт:" + product.getName()));

                    return product1.getStockQuantity() >= 1;
                });

        if (isProductInStock) {
            orders.getProducts().forEach(product -> {
                Product product1 = productRepository.findById(product.getUuid()).get();
                int newStock = product1.getStockQuantity() - 1;
                product1.setStockQuantity(newStock);

                if (newStock == 0) {
                    product1.setInStock(false);
                }
                productRepository.saveAndFlush(product1);
            });

            orders.setStatus(Status.ORDERED);
            orderRepository.saveAndFlush(orders);
        } else {
            System.out.println("logi");
        }
    }

    @Override
    public void deleteByUUID(UUID uuid) {
        orderRepository.deleteById(uuid);
    }

    @Override
    public Optional<Orders> findByUUID(UUID uuid) {
        return orderRepository.findById(uuid);
    }

    @Override
    public List<Orders> findAll() {
        return null;
    }

    @Override
    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void updateOrderStatus() {
        List<Orders> orders = orderRepository.findAll();
        for (Orders order : orders) {
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

    @Override
    @Transactional
    public void editOrder(Orders orders) {
        Optional<Orders> existingOrder = orderRepository.findById(orders.getUuid());

        if (existingOrder.isPresent()) {
            Orders currOrders = existingOrder.get();
            currOrders.setCustomerName(orders.getCustomerName());
            currOrders.setCustomerAddress(orders.getCustomerAddress());
            currOrders.setOrderDate(orders.getOrderDate());
            currOrders.setOrderCost(orders.getOrderCost());

            orderRepository.saveAndFlush(currOrders);
        } else {
            System.out.println("logi");
        }
    }
}
