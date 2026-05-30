package com.mes.factory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOrderSearchDto {

    private String status;
    private String productName;
    private String startDate;
    private String endDate;
    private int page;
    private int pageSize;

    // offset 계산
    public int getOffset() {
        return (page - 1) * pageSize;
    }
}
