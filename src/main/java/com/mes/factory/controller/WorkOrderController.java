package com.mes.factory.controller;

import com.mes.factory.dto.WorkOrderDto;
import com.mes.factory.service.ProductService;
import com.mes.factory.service.WorkOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/workorder")
public class WorkOrderController {

    private final WorkOrderService workOrderService;
    private final ProductService productService;


    public WorkOrderController(WorkOrderService workOrderService, ProductService productService) {
        this.workOrderService = workOrderService;
        this.productService = productService;
    }

    // 목록
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("workOrderList", workOrderService.getWorkOrderList());
        return "workorder/list";
    }

    //등록 폼
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("workOrder", new WorkOrderDto());
        model.addAttribute("productList", productService.getProductList());
        return "workorder/form";
    }

    // 등록 처리
    @PostMapping("/create")
    public String create(@ModelAttribute WorkOrderDto workOrderDto) {
        workOrderService.createWorkOrder(workOrderDto);
        return "redirect:/workorder/list";
    }

    // 상태 변경
    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable int id, @RequestParam String status) {
        WorkOrderDto dto = new WorkOrderDto();
        dto.setOrderId(id);
        dto.setStatus(status);
        workOrderService.updateStatus(dto);
        return "redirect:/workorder/list";
    }

    // 삭제
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        workOrderService.deleteWorkOrder(id);
        return "redirect:/workorder/list";
    }
}
