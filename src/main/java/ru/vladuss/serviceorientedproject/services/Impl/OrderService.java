package ru.vladuss.serviceorientedproject.services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderService implements IOrderService<Orders, UUID> {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;

    @Autowired
    public OrderService(IOrderRepository orderRepository, IProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addOrder(Orders orders) {
        boolean isProductInStock = orders.getProducts().stream()
                .allMatch(product -> {
                    Product product1 = productRepository.findById(product.getUuid()).orElseThrow(() ->
                            new IllegalArgumentException("Не нашли продукт: " + product.getName()));
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
            logger.info("Заказ {} успешно создан со статусом ORDERED.", orders.getUuid());
        } else {
            logger.warn("Попытка создания заказа не удалась: один или несколько товаров отсутствуют на складе.");
        }
    }

    @Override
    public void deleteByUUID(UUID uuid) {
        orderRepository.deleteById(uuid);
        logger.info("Заказ с UUID {} был удалён.", uuid);
    }

    @Override
    public Optional<Orders> findByUUID(UUID uuid) {
        Optional<Orders> existingOrders = orderRepository.findById(uuid);
        if (existingOrders.isPresent()) {
            logger.info("Заказ {} найден.", uuid);
            return Optional.of(existingOrders.get());
        } else {
            logger.warn("Заказ с UUID {} не найден.", uuid);
            return null;
        }
    }


    @Override
    public List<Orders> findAll() {
        logger.info("Получение всех заказов.");
        return orderRepository.findAll();
    }

    @Override
    public void updateOrderStatus() {
        List<Orders> orders = orderRepository.findAll();
        for (Orders order : orders) {
            Status currStatus = order.getStatus();
            switch (currStatus) {
                case ORDERED:
                    order.setStatus(Status.CONFIRMED);
                    logger.info("Статус заказа {} обновлён на CONFIRMED.", order.getUuid());
                    break;
                case CONFIRMED:
                    order.setStatus(Status.ASSEMBLING);
                    logger.info("Статус заказа {} обновлён на ASSEMBLING.", order.getUuid());
                    break;
                case ASSEMBLING:
                    order.setStatus(Status.PACKED);
                    logger.info("Статус заказа {} обновлён на PACKED.", order.getUuid());
                    break;
                case PACKED:
                    order.setStatus(Status.SHIPPED);
                    logger.info("Статус заказа {} обновлён на SHIPPED.", order.getUuid());
                    break;
                case SHIPPED:
                    order.setStatus(Status.IN_TRANSIT);
                    logger.info("Статус заказа {} обновлён на IN_TRANSIT.", order.getUuid());
                    break;
                case IN_TRANSIT:
                    order.setStatus(Status.AWAITING_PICKUP);
                    logger.info("Статус заказа {} обновлён на AWAITING_PICKUP.", order.getUuid());
                    break;
                case AWAITING_PICKUP:
                    order.setStatus(Status.DELIVERED);
                    logger.info("Статус заказа {} обновлён на DELIVERED.", order.getUuid());
                    break;
                default:
                    logger.warn("Неизвестный статус для заказа {}: {}", order.getUuid(), currStatus);
                    break;
            }
            orderRepository.saveAndFlush(order);
        }
    }

    @Override
    public void editOrder(Orders orders) {
        Optional<Orders> existingOrder = orderRepository.findById(orders.getUuid());

        if (existingOrder.isPresent()) {
            Orders currOrders = existingOrder.get();
            currOrders.setCustomerName(orders.getCustomerName());
            currOrders.setCustomerAddress(orders.getCustomerAddress());
            currOrders.setOrderDate(orders.getOrderDate());
            currOrders.setOrderCost(orders.getOrderCost());

            orderRepository.saveAndFlush(currOrders);
            logger.info("Заказ {} был обновлён.", orders.getUuid());
        } else {
            logger.warn("Заказ с UUID {} не найден, невозможно обновить.", orders.getUuid());
        }
    }
}
