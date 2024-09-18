package ru.vladuss.serviceorientedproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vladuss.serviceorientedproject.entity.Order;
import ru.vladuss.serviceorientedproject.services.IOrderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public ResponseEntity<EntityModel<Order>> addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
        EntityModel<Order> orderModel = EntityModel.of(order);
        orderModel.add(linkTo(methodOn(OrderController.class).getOrder(order.getUuid())).withSelfRel());
        return ResponseEntity.created(orderModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(orderModel);
    }

    @GetMapping("/{orderUUID}")
    public ResponseEntity<EntityModel<Order>> getOrder(@PathVariable UUID orderUUID) {
        Optional<Order> orderOpt = orderService.findByUUID(orderUUID);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            EntityModel<Order> orderModel = EntityModel.of(order);
            addOrderLinks(orderModel, order);
            return ResponseEntity.ok(orderModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<EntityModel<Order>>> getAllOrders() {
        List<Order> orders = orderService.findAll();
        List<EntityModel<Order>> orderModels = orders.stream().map(order -> {
            EntityModel<Order> orderModel = EntityModel.of(order);
            addOrderLinks(orderModel, order);
            return orderModel;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(orderModels);
    }

    @PatchMapping("/update")
    public ResponseEntity<EntityModel<Order>> editOrder(@RequestBody Order order) {
        orderService.editOrder(order);
        EntityModel<Order> orderModel = EntityModel.of(order);
        orderModel.add(linkTo(methodOn(OrderController.class).getOrder(order.getUuid())).withSelfRel());
        return ResponseEntity.ok(orderModel);
    }

    @DeleteMapping("/delete/{orderUUID}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderUUID) {
        Optional<Order> orderOpt = orderService.findByUUID(orderUUID);
        if (orderOpt.isPresent()) {
            orderService.deleteByUUID(orderUUID);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void addOrderLinks(EntityModel<Order> orderModel, Order order) {
        orderModel.add(linkTo(methodOn(OrderController.class).getOrder(order.getUuid())).withSelfRel());
        orderModel.add(linkTo(methodOn(OrderController.class).editOrder(order)).withRel("update"));
        orderModel.add(linkTo(methodOn(OrderController.class).deleteOrder(order.getUuid())).withRel("delete"));
    }
}
