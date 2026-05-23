package com.mes.factory.service;

import com.mes.factory.dto.ProductDto;
import com.mes.factory.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<ProductDto> getProductList() {
        return productMapper.selectProductList();
    }

    public ProductDto getProductById(int productId) {
        return productMapper.selectProductById(productId);
    }

    public void createProduct(ProductDto productDto) {
        productMapper.insertProduct(productDto);
    }

    public void updateProduct(ProductDto productDto) {
        productMapper.updateProduct(productDto);
    }

    public void deleteProduct(int productId) {
        productMapper.deleteProduct(productId);
    }
}
