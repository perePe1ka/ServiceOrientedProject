package ru.vladuss.serviceorientedproject.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import ru.vladuss.serviceorientedproject.entity.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductActionModel extends RepresentationModel<ProductActionModel> {
    private Product product;

    private final Map<String, Map<String, String>> action = new HashMap<>();

    public ProductActionModel(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Map<String, Map<String, String>> getAction() {
        return action;
    }

    public void addProduct(String name, String method, WebMvcLinkBuilder link) {
        Map<String, String> actionField = new HashMap<>();
        actionField.put("href", link.withSelfRel().getHref());
        actionField.put("method", method);
        action.put(name, actionField);
    }
}
