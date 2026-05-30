package com.mes.factory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionResultSearchDto {

    private String productName;
    private String defectType;
    private String worker;
    private String startDate;
    private String endDate;
    private int page;
    private int pageSize;

    public int getOffset() {
        return (page - 1) * pageSize;
    }
}
