package com.mes.factory.mapper;

import com.mes.factory.dto.ProductionResultDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductionResultMapper {

    // 전체 실적 목록 조회
    List<ProductionResultDto> selectResultList();

    // 특정 작업지시에 대한 실적 조회
    ProductionResultDto selectResultById(int resultId);

    // 실적 등록
    void insertResult(ProductionResultDto dto);

    // 실적 수정
    void updateResult(ProductionResultDto dto);

    // 실적 삭제
    void deleteResult(int resultId);
}
