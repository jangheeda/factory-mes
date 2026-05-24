package com.mes.factory.service;

import com.mes.factory.dto.WorkOrderDto;
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

}
