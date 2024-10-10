package ru.vladuss.serviceorientedproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vladuss.serviceorientedproject.entity.Orders;
import ru.vladuss.serviceorientedproject.model.OrderActionModel;
import ru.vladuss.serviceorientedproject.services.IOrderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final IOrderService<Orders, UUID> orderService;

    @Autowired
    public OrderController(IOrderService<Orders, UUID> orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public ResponseEntity<EntityModel<Orders>> addOrder(@RequestBody Orders orders) {
        orderService.addOrder(orders);
        EntityModel<Orders> orderModel = EntityModel.of(orders);
        orderModel.add(linkTo(methodOn(OrderController.class).getOrder(orders.getUuid())).withSelfRel());
        return ResponseEntity.created(orderModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(orderModel);
    }

    @GetMapping("/{orderUUID}")
    public ResponseEntity<OrderActionModel> getOrder(@PathVariable UUID orderUUID) {
        Optional<Orders> orderOpt = orderService.findByUUID(orderUUID);

        if (orderOpt.isPresent()) {
            Orders orders = orderOpt.get();

            OrderActionModel orderModel = new OrderActionModel(orders);

            orderModel.add(linkTo(methodOn(OrderController.class).getOrder(orderUUID)).withSelfRel());
            orderModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("get-all"));
            orderModel.add(linkTo(methodOn(OrderController.class).deleteOrder(orderUUID)).withRel("delete"));
            orderModel.add(linkTo(methodOn(OrderController.class).editOrder(orders)).withRel("edit"));

            orderModel.addOrders("update", "PUT", linkTo(methodOn(OrderController.class).editOrder(orders)));
            orderModel.addOrders("delete", "DELETE", linkTo(methodOn(OrderController.class).deleteOrder(orderUUID)));

            return ResponseEntity.ok(orderModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<OrderActionModel>> getAllOrders() {
        List<Orders> orders = orderService.findAll();

        List<OrderActionModel> orderModels = orders.stream().map(order -> {
            OrderActionModel orderModel = new OrderActionModel(order);

            orderModel.add(linkTo(methodOn(OrderController.class).getOrder(order.getUuid())).withSelfRel());
            orderModel.add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("get-all"));
            orderModel.add(linkTo(methodOn(OrderController.class).deleteOrder(order.getUuid())).withRel("delete"));
            orderModel.add(linkTo(methodOn(OrderController.class).editOrder(order)).withRel("edit"));

            orderModel.addOrders("update", "PUT", linkTo(methodOn(OrderController.class).editOrder(order)));
            orderModel.addOrders("delete", "DELETE", linkTo(methodOn(OrderController.class).deleteOrder(order.getUuid())));

            return orderModel;
        }).toList();

        return ResponseEntity.ok(orderModels);
    }

    @PatchMapping("/update")
    public ResponseEntity<EntityModel<Orders>> editOrder(@RequestBody Orders orders) {
        orderService.editOrder(orders);
        EntityModel<Orders> orderModel = EntityModel.of(orders);
        orderModel.add(linkTo(methodOn(OrderController.class).getOrder(orders.getUuid())).withSelfRel());
        return ResponseEntity.ok(orderModel);
    }

    @DeleteMapping("/delete/{orderUUID}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderUUID) {
        Optional<Orders> orderOpt = orderService.findByUUID(orderUUID);
        if (orderOpt.isPresent()) {
            orderService.deleteByUUID(orderUUID);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
