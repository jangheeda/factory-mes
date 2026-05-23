package com.mes.factory.mapper;

import com.mes.factory.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductDto> selectProductList();

    ProductDto selectProductById(int productId);

    void insertProduct(ProductDto productDto);

    void updateProduct(ProductDto productDto);

    void deleteProduct(int productId);

}
