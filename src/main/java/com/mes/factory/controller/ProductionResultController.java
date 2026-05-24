package com.mes.factory.controller;

import com.mes.factory.dto.ProductionResultDto;
import com.mes.factory.service.ProductionResultService;
import com.mes.factory.service.WorkOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/result")
public class ProductionResultController {

    private final ProductionResultService productionResultService;
    private final WorkOrderService workOrderService;

    public ProductionResultController(ProductionResultService productionResultService, WorkOrderService workOrderService) {
        this.productionResultService = productionResultService;
        this.workOrderService = workOrderService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("resultList", productionResultService.getResultList());
        return "result/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("result", new ProductionResultDto());
        model.addAttribute("workOrderList", workOrderService.getWorkOrderList());
        return "result/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ProductionResultDto dto) {
        productionResultService.createResult(dto);
        return "redirect:/result/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productionResultService.deleteResult(id);
        return "redirect:/result/list";
    }
}
