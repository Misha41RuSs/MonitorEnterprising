package com.kp.monitorenterprising.model;

import java.time.LocalDate;

public class Order {
    private int orderId;
    private String monitorName;
    private int quantity;
    private LocalDate orderDate;
    private double totalPrice;
    private String status;
    private String username;

    public Order(int orderId, String monitorName, int quantity, LocalDate orderDate, double totalPrice, String status, String username) {
        this.orderId = orderId;
        this.monitorName = monitorName;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.username = username;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }
}
