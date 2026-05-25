package com.mes.factory.service;

import com.mes.factory.dto.DashboardDto;
import com.mes.factory.mapper.DashboardMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final DashboardMapper dashboardMapper;

    public DashboardService(DashboardMapper dashboardMapper) {
        this.dashboardMapper = dashboardMapper;
    }

    public DashboardDto getDashboardData() {
        DashboardDto dashboard = dashboardMapper.selectTodayResultSummary();

        List<Map<String, Object>> statusList = dashboardMapper.selectWorkOrderStatusCount();
        for(Map<String, Object> map : statusList) {
            String status = (String)map.get("status");

            int cnt = ((Long)map.get("cnt")).intValue();

            if(status.equals("대기")) dashboard.setWaitCount(cnt);
            else if(status.equals("진행중")) dashboard.setInProgressCount(cnt);
            else if(status.equals("완료")) dashboard.setDoneCount(cnt);
        }
        return dashboard;
    }
}
