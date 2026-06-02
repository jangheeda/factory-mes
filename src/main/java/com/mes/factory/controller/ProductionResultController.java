package com.mes.factory.controller;

import com.mes.factory.dto.ProductionResultDto;
import com.mes.factory.dto.ProductionResultSearchDto;
import com.mes.factory.dto.WorkOrderDto;
import com.mes.factory.service.DashboardService;
import com.mes.factory.service.ProductionResultService;
import com.mes.factory.service.WorkOrderService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/result")
public class ProductionResultController {

    private final ProductionResultService productionResultService;
    private final WorkOrderService workOrderService;
    private final SimpMessagingTemplate messagingTemplate;
    private final DashboardService dashboardService;

    public ProductionResultController(ProductionResultService productionResultService,
                                      WorkOrderService workOrderService,
                                      SimpMessagingTemplate messagingTemplate,
                                      DashboardService dashboardService) {
        this.productionResultService = productionResultService;
        this.workOrderService = workOrderService;
        this.messagingTemplate = messagingTemplate;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/list")
    public String list(@ModelAttribute ProductionResultSearchDto searchDto, Model model) {

        // 기본값 설정
        if (searchDto.getPage() == 0) searchDto.setPage(1);
        if (searchDto.getPageSize() == 0) searchDto.setPageSize(10);

        List<ProductionResultDto> resultList = productionResultService.getResultListBySearch(searchDto);

        int totalCount = productionResultService.getResultCount(searchDto);
        int totalPages = productionResultService.getTotalPages(totalCount, searchDto.getPageSize());

        model.addAttribute("resultList", resultList);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPages", totalPages);
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

        // 대시보드 실시간 업데이트
        messagingTemplate.convertAndSend("/topic/dashboard", dashboardService.getDashboardData());
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

        // 대시보드 실시간 업데이트
        messagingTemplate.convertAndSend("/topic/dashboard", dashboardService.getDashboardData());
        return "redirect:/result/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productionResultService.deleteResult(id);

        // 대시보드 실시간 업데이트
        messagingTemplate.convertAndSend("/topic/dashboard", dashboardService.getDashboardData());
        return "redirect:/result/list";
    }
}
