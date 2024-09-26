package ru.vladuss.serviceorientedproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vladuss.serviceorientedproject.entity.Orders;
import ru.vladuss.serviceorientedproject.entity.Product;
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
    public ResponseEntity<EntityModel<Orders>> addOrder(@RequestBody Orders orders) {
        orderService.addOrder(orders);
        EntityModel<Orders> orderModel = EntityModel.of(orders);
        orderModel.add(linkTo(methodOn(OrderController.class).getOrder(orders.getUuid())).withSelfRel());
        return ResponseEntity.created(orderModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(orderModel);
    }

    @GetMapping("/{orderUUID}")
    public ResponseEntity<EntityModel<Orders>> getOrder(@PathVariable UUID orderUUID) {
        Optional<Orders> orderOpt = orderService.findByUUID(orderUUID);
        if (orderOpt.isPresent()) {
            Orders orders = orderOpt.get();
            EntityModel<Orders> orderModel = EntityModel.of(orders);
            addOrderLinks(orderModel, orders);
            return ResponseEntity.ok(orderModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<EntityModel<Orders>>> getAllOrders() {
        List<Orders> orders = orderService.findAll();
        List<EntityModel<Orders>> orderModels = orders.stream().map(order -> {
            EntityModel<Orders> orderModel = EntityModel.of(order);
            addOrderLinks(orderModel, order);
            return orderModel;
        }).collect(Collectors.toList());
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

    private void addOrderLinks(EntityModel<Orders> orderModel, Orders orders) {
        orderModel.add(linkTo(methodOn(OrderController.class).getOrder(orders.getUuid())).withSelfRel());
        orderModel.add(linkTo(methodOn(OrderController.class).editOrder(orders)).withRel("update"));
        orderModel.add(linkTo(methodOn(OrderController.class).deleteOrder(orders.getUuid())).withRel("delete"));
    }
}
