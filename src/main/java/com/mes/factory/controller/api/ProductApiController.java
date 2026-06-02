package com.mes.factory.controller.api;

import com.mes.factory.dto.ProductDto;
import com.mes.factory.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    // 목록 조회
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProductList());
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable int id) {
        ProductDto product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    // 등록
    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto productDto) {
        productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable int id, @RequestBody ProductDto productDto) {
        productDto.setProductId(id);
        productService.updateProduct(productDto);
        return ResponseEntity.ok().build();
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
