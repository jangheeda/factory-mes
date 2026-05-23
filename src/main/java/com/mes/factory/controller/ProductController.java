package com.mes.factory.controller;

import com.mes.factory.dto.ProductDto;
import com.mes.factory.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //목록
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("productList", productService.getProductList());
        return "product/list";
    }

    // 등록 폼
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new ProductDto());
        return "product/form";
    }

    // 등록 처리
    @PostMapping("/create")
    public String create(@ModelAttribute ProductDto productDto) {
        productService.createProduct(productDto);
        return "redirect:/product/list";
    }

    //수정 폼
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product/form";
    }

    //수정 처리
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable int id, @ModelAttribute ProductDto productDto) {
        productDto.setProductId(id);
        productService.updateProduct(productDto);
        return "redirect:/product/list";
    }

    //삭제
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/product/list";
    }

}
