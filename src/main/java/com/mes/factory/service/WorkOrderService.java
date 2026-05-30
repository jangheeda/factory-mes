package com.mes.factory.service;

import com.mes.factory.dto.WorkOrderDto;
import com.mes.factory.dto.WorkOrderSearchDto;
import com.mes.factory.mapper.WorkOrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkOrderService {

    private final WorkOrderMapper workOrderMapper;

    public WorkOrderService(WorkOrderMapper workOrderMapper) {
        this.workOrderMapper = workOrderMapper;
    }

    public List<WorkOrderDto> getWorkOrderList() {
        return workOrderMapper.selectWorkOrderList();
    }

    public WorkOrderDto getWorkOrderById(int orderId) {
        return workOrderMapper.selectWorkOrderById(orderId);
    }

    public void createWorkOrder(WorkOrderDto workOrderDto) {
        workOrderMapper.insertWorkOrder(workOrderDto);
    }

    public void updateStatus(WorkOrderDto workOrderDto) {
        workOrderMapper.updateStatus(workOrderDto);
    }

    public void deleteWorkOrder(int orderId) {
        workOrderMapper.deleteWorkOrder(orderId);
    }

    // 검색 조건으로 작업지시 목록 조회
    public List<WorkOrderDto> getWorkOrderListBySearch(WorkOrderSearchDto searchDto) {
        // 기본값 설정
        if (searchDto.getPage() == 0) searchDto.setPage(1);
        if (searchDto.getPageSize() == 0) searchDto.setPageSize(10);

        return workOrderMapper.selectWorkOrderListBySearch(searchDto);
    }

    // 전체 건수 조회
    public int getWorkOrderCount(WorkOrderSearchDto searchDto) {
        return workOrderMapper.selectWorkOrderCount(searchDto);
    }

    // 전체 페이지 수 계산
    public int getTotalPages(int totalCount, int pageSize) {
        return (int) Math.ceil((double) totalCount / pageSize);
    }

}
