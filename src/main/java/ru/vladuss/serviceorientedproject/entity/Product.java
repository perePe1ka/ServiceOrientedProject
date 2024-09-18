package ru.vladuss.serviceorientedproject.entity;

import jakarta.persistence.JoinColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    private String name;

    private String description;

    private Double price;

    private Integer stockQuantity;

    private Boolean inStock;

    private Set<Order> orders;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    @Column(name = "stock_quantity")
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

    @ManyToOne
    @JoinColumn(name = "order_uuid", referencedColumnName = "uuid", nullable = false)
    public Set<Order>  getOrder() {
        return orders;
    }

    public void setOrder(Set<Order>  orders) {
        this.orders = orders;
    }

    public Product(String name, String description, Double price, Integer stockQuantity, Set<Order>  orders, Boolean inStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.orders = orders;
        this.inStock = inStock;
    }

    public Product() {

    }
}
