package com.mes.factory.controller.api;

import com.mes.factory.dto.WorkOrderDto;
import com.mes.factory.dto.WorkOrderSearchDto;
import com.mes.factory.service.WorkOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderApiController {

    private final WorkOrderService workOrderService;

    public WorkOrderApiController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    // 목록 조회
    @GetMapping
    public ResponseEntity<List<WorkOrderDto>> getWorkOrders(@ModelAttribute WorkOrderSearchDto searchDto) {
        if (searchDto.getPage() == 0) searchDto.setPage(1);
        if (searchDto.getPageSize() == 0) searchDto.setPageSize(10);
        return ResponseEntity.ok(workOrderService.getWorkOrderListBySearch(searchDto));
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderDto> getWorkOrder(@PathVariable int id) {
        WorkOrderDto workOrder = workOrderService.getWorkOrderById(id);
        if (workOrder == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(workOrder);
    }

    // 등록
    @PostMapping
    public ResponseEntity<Void> createWorkOrder(@RequestBody WorkOrderDto workOrderDto) {
        workOrderService.createWorkOrder(workOrderDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 상태 변경
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable int id, @RequestBody WorkOrderDto workOrderDto) {
        workOrderDto.setOrderId(id);
        workOrderService.updateStatus(workOrderDto);
        return ResponseEntity.ok().build();
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable int id) {
        workOrderService.deleteWorkOrder(id);
        return ResponseEntity.ok().build();
    }
}
