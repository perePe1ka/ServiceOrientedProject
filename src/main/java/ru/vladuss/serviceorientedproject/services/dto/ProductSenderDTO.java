package ru.vladuss.serviceorientedproject.services.dto;

import ru.vladuss.serviceorientedproject.entity.Orders;

public class ProductSenderDTO extends BaseDto{
    private String name;

    private String description;

    private Double price;

    private Integer stockQuantity;

    private Boolean inStock;

    private Orders orders;

    public ProductSenderDTO(String uuid, Integer stockQuantity, Boolean inStock) {
        super(uuid);
        this.stockQuantity = stockQuantity;
        this.inStock = inStock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
