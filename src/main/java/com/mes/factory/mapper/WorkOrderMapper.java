package com.mes.factory.mapper;

import com.mes.factory.dto.WorkOrderDto;
import com.mes.factory.dto.WorkOrderSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkOrderMapper {

    List<WorkOrderDto> selectWorkOrderList();

    WorkOrderDto selectWorkOrderById(int orderId);

    void insertWorkOrder(WorkOrderDto workOrderDto);

    void updateStatus(WorkOrderDto workOrderDto);

    void deleteWorkOrder(int orderId);

    List<WorkOrderDto> selectWorkOrderListBySearch(WorkOrderSearchDto searchDto);

    int selectWorkOrderCount(WorkOrderSearchDto searchDto);
}
