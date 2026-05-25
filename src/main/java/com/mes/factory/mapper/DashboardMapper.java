package com.mes.factory.mapper;

import com.mes.factory.dto.DashboardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DashboardMapper {

    // 작업지시 상태별 건수 조회
    List<Map<String, Object>> selectWorkOrderStatusCount();

    // 오늘 생산 실적 합계 조회
    DashboardDto selectTodayResultSummary();
}
