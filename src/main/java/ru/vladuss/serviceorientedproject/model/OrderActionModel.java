package ru.vladuss.serviceorientedproject.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import ru.vladuss.serviceorientedproject.entity.Orders;

import java.util.HashMap;
import java.util.Map;


public class OrderActionModel extends RepresentationModel<OrderActionModel> {
    private Orders orders;

    private final Map<String, Map<String, String>> action = new HashMap<>();

    public OrderActionModel(Orders orders) {
        this.orders = orders;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Map<String, Map<String, String>> getAction() {
        return action;
    }

    public void addOrders(String name, String method, WebMvcLinkBuilder link) {
        Map<String, String> actionField = new HashMap<>();
        actionField.put("href", link.withSelfRel().getHref());
        actionField.put("method", method);
        action.put(name, actionField);
    }
}
