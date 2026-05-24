package com.mes.factory.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDto {

    private int productId;
    private String productName;
    private String productCode;
    private String unit;
    private LocalDateTime createdAt;
}
