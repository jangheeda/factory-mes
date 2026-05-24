package com.mes.factory.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class WorkOrderDto {

    private int orderId;
    private int productId;
    private String productName;
    private int targetQty;
    private String status;
    private LocalDate orderDate;
    private LocalDateTime createdAt;
}
