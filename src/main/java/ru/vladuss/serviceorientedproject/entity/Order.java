package ru.vladuss.serviceorientedproject.entity;

import javax.persistence.Entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    private LocalDateTime orderDate;
    private Set<Product> products;

    private String customerName;

    private String customerAddress;

    private String status;

    public Order(LocalDateTime orderDate, String customerName, String customerAddress, String status) {
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.status = status;
    }

    public Order() {
    }

    @Column(name = "order_date")
    public LocalDateTime getOrderDate() {
        return orderDate;
    }


    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "orders", cascade = CascadeType.REMOVE)
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    @Column(name = "customer_name")
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    @Column(name = "customer_address")
    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
