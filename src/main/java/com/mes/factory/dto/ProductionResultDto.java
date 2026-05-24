package com.mes.factory.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductionResultDto {

    private int resultId;
    private int orderId;
    private String productName;
    private int targetQty;
    private int goodQty;
    private int defectQty;
    private String worker;
    private LocalDate resultDate;
    private LocalDateTime createdAt;
}
