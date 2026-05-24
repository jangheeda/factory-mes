package com.mes.factory.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WorkOrderDto {

    private int orderId;
    private int productId;
    private String productName;
    private int targetQty;
    private String status;
    private LocalDate orderDate;
    private LocalDateTime createdAt;


    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTargetQty() {
        return targetQty;
    }
    public void setTargetQty(int targetQty) {
        this.targetQty = targetQty;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
