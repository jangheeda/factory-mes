package com.mes.factory.service;

import com.mes.factory.dto.ProductionResultDto;
import com.mes.factory.mapper.ProductionResultMapper;
import org.springframework.stereotype.Service;

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

    public void createResult(ProductionResultDto dto) {
        productionResultMapper.insertResult(dto);
    }

    public void updateResult(ProductionResultDto dto) { productionResultMapper.updateResult(dto); }

    public void deleteResult(int resultId) {
        productionResultMapper.deleteResult(resultId);
    }
}
