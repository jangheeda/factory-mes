package com.mes.factory.controller;

import com.mes.factory.dto.WorkOrderDto;
import com.mes.factory.dto.WorkOrderSearchDto;
import com.mes.factory.service.ProductService;
import com.mes.factory.service.WorkOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String list(@ModelAttribute WorkOrderSearchDto searchDto, Model model) {

        // 기본값 설정
        if (searchDto.getPage() == 0) searchDto.setPage(1);
        if (searchDto.getPageSize() == 0) searchDto.setPageSize(10);

        // 검색 결과 목록
        List<WorkOrderDto> workOrderList = workOrderService.getWorkOrderListBySearch(searchDto);

        // 전체 건수 및 페이지 수
        int totalCount = workOrderService.getWorkOrderCount(searchDto);
        int totalPages = workOrderService.getTotalPages(totalCount, searchDto.getPageSize());

        model.addAttribute("workOrderList", workOrderList);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("totalPages", totalPages);

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
