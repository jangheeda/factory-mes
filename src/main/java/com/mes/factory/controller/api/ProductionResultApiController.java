package com.mes.factory.controller.api;

import com.mes.factory.dto.ProductionResultDto;
import com.mes.factory.dto.ProductionResultSearchDto;
import com.mes.factory.service.ProductionResultService;
import com.mes.factory.service.WorkOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ProductionResultApiController {

    private final ProductionResultService productionResultService;
    private final WorkOrderService workOrderService;

    public ProductionResultApiController(ProductionResultService productionResultService, WorkOrderService workOrderService) {
        this.productionResultService = productionResultService;
        this.workOrderService = workOrderService;
    }

    // 목록 조회
    @GetMapping
    public ResponseEntity<List<ProductionResultDto>> getResults(@ModelAttribute ProductionResultSearchDto searchDto) {
        if (searchDto.getPage() == 0) searchDto.setPage(1);
        if (searchDto.getPageSize() == 0) searchDto.setPageSize(10);
        return ResponseEntity.ok(productionResultService.getResultListBySearch(searchDto));
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductionResultDto> getResult(@PathVariable int id) {
        ProductionResultDto result = productionResultService.getResultById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    // 등록
    @PostMapping
    public ResponseEntity<String> createResult(@RequestBody ProductionResultDto dto) {
        int targetQty = workOrderService.getWorkOrderById(dto.getOrderId()).getTargetQty();
        String errorMessage = productionResultService.createResult(dto, targetQty);
        if (errorMessage != null) {
            return ResponseEntity.badRequest().body(errorMessage);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateResult(@PathVariable int id, @RequestBody ProductionResultDto dto) {
        dto.setResultId(id);
        productionResultService.updateResult(dto);
        return ResponseEntity.ok().build();
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable int id) {
        productionResultService.deleteResult(id);
        return ResponseEntity.ok().build();
    }
}
