package com.mes.factory.service;

import com.mes.factory.dto.ProductionResultDto;
import com.mes.factory.mapper.ProductionResultMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductionResultService {

    private final ProductionResultMapper productionResultMapper;

    public ProductionResultService(ProductionResultMapper productionResultMapper) {
        this.productionResultMapper = productionResultMapper;
    }

    public List<ProductionResultDto> getResultList() {
        return productionResultMapper.selectResultList();
    }

    public ProductionResultDto getResultById(int resultId) {
        return productionResultMapper.selectResultById(resultId);
    }

    public String createResult(ProductionResultDto dto, int targetQty) {

        // 유효성 검사
        // 작업자 빈칸 체크
        if (dto.getWorker() == null || dto.getWorker().trim().isEmpty()) {
            return "작업자를 입력해주세요";
        }
        // 미래 날짜 체크
        if (dto.getResultDate().isAfter(LocalDate.now())) {
            return "오늘 날짜까지만 등록할 수 있습니다.";
        }

        // 목표 수량 초과 체크
        int totalGoodQty = productionResultMapper.selectTotalGoodQtyByOrderId(dto.getOrderId());
        if (totalGoodQty + dto.getGoodQty() > targetQty) {
            return "목표 수량을 초과할 수 없습니다.";
        }
        productionResultMapper.insertResult(dto);

        return null;
    }

    public void updateResult(ProductionResultDto dto) { productionResultMapper.updateResult(dto); }

    public void deleteResult(int resultId) {
        productionResultMapper.deleteResult(resultId);
    }

}
