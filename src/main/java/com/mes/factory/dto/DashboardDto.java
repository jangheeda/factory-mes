package com.mes.factory.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DashboardDto {

    // 작업지시 상태별 건수
    private int waitCount;
    private int inProgressCount;
    private int doneCount;

    // 오늘 생산 실적
    private int totalGoodQty;
    private int totalDefectQty;

    // 불량률 계산
    public double getDefectRate() {
        int total = totalGoodQty + totalDefectQty;
        if (total == 0) return 0;
        return Math.round((double) totalDefectQty / total * 1000) / 10.0;
    }

    // 작업지시별 진척률 목록
    private List<Map<String, Object>> progressRateList;
}
