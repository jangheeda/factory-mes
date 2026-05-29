package com.mes.factory.controller;

import com.mes.factory.dto.ProductionResultDto;
import com.mes.factory.dto.WorkOrderDto;
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
    public String create(@ModelAttribute ProductionResultDto dto, Model model) {
        WorkOrderDto workOrder = workOrderService.getWorkOrderById(dto.getOrderId());

        String errorMessage = productionResultService.createResult(dto, workOrder.getTargetQty());

        if(errorMessage != null) {
            model.addAttribute("result", dto);
            model.addAttribute("workOrderList", workOrderService.getWorkOrderList());
            model.addAttribute("errorMessage", errorMessage);
            return "result/form";
        }

        return "redirect:/result/list";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("result", productionResultService.getResultById(id));
        model.addAttribute("workOrderList", workOrderService.getWorkOrderList());
        return "result/form";
    }

    // 수정 처리
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable int id, @ModelAttribute ProductionResultDto dto) {
        dto.setResultId(id);
        productionResultService.updateResult(dto);
        return "redirect:/result/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productionResultService.deleteResult(id);
        return "redirect:/result/list";
    }
}
