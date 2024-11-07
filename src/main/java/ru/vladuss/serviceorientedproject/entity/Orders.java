package ru.vladuss.serviceorientedproject.entity;

import jakarta.persistence.*;
import ru.vladuss.serviceorientedproject.constants.Status;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Orders extends BaseEntity{
    private LocalDateTime orderDate;
    private Set<Product> products;

    private String customerName;

    private String customerAddress;

    private long orderCost;

    private Instant timeOfSwapStatus;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Orders(LocalDateTime orderDate, String customerName, String customerAddress, Status status, long orderCost, Instant timeOfSwapStatus) {
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.status = status;
        this.orderCost = orderCost;
        this.timeOfSwapStatus = timeOfSwapStatus;
    }

    public Orders() {
    }

    @Column(name = "time_of_swap_status")
    public Instant getTimeOfSwapStatus() {
        return timeOfSwapStatus;
    }

    public void setTimeOfSwapStatus(Instant timeOfSwapStatus) {
        this.timeOfSwapStatus = timeOfSwapStatus;
    }

    @Column(name = "orders_date")
    public LocalDateTime getOrderDate() {
        return orderDate;
    }


    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.REMOVE)
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
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "orders_cost", nullable = false)
    public long getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(long orderCost) {
        this.orderCost = orderCost;
    }
}
