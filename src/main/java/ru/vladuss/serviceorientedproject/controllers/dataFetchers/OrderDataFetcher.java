package ru.vladuss.serviceorientedproject.controllers.dataFetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vladuss.serviceorientedproject.entity.Orders;
import ru.vladuss.serviceorientedproject.services.IOrderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DgsComponent
public class OrderDataFetcher {
    private final IOrderService<Orders, UUID> orderService;

    @Autowired
    public OrderDataFetcher(IOrderService<Orders, UUID> orderService) {
        this.orderService = orderService;
    }

    @DgsMutation
    public Orders addOrder(@InputArgument Orders orders) {
        orderService.addOrder(orders);
        return orders;
    }

    @DgsQuery
    public Orders getOrder(@InputArgument UUID orderUUID) {
        Optional<Orders> orderOpt = orderService.findByUUID(orderUUID);
        if (orderOpt.isPresent()) {
            Orders orders = orderOpt.get();
            return orders;
        } else {
            return null;
        }
    }

    @DgsQuery
    public List<Orders> getAllOrders() {
        List<Orders> orders = orderService.findAll();
        return orders;
    }


    @DgsMutation
    public Orders editOrder(@InputArgument Orders orders) {
        orderService.editOrder(orders);
        return orders;
    }

    @DgsMutation
    public void deleteOrder(@InputArgument UUID orderUUID) {
        Optional<Orders> orderOpt = orderService.findByUUID(orderUUID);
        if (orderOpt.isPresent()) {
            orderService.deleteByUUID(orderUUID);
        }
    }

}
