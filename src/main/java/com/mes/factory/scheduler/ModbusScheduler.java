package com.mes.factory.scheduler;

import com.mes.factory.dto.ProductionResultDto;
import com.mes.factory.dto.WorkOrderDto;
import com.mes.factory.service.DashboardService;
import com.mes.factory.service.ProductionResultService;
import com.mes.factory.service.WorkOrderService;
import lombok.Locked;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.TCPMasterConnection;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.LocalDate;

@Component
public class ModbusScheduler {

    private boolean isRunning = true;

    private final ProductionResultService productionResultService;
    private final WorkOrderService workOrderService;
    private final SimpMessagingTemplate messagingTemplate;
    private final DashboardService dashboardService;

    public ModbusScheduler(ProductionResultService productionResultService,
                           WorkOrderService workOrderService,
                           SimpMessagingTemplate messagingTemplate,
                           DashboardService dashboardService) {
        this.productionResultService = productionResultService;
        this.workOrderService = workOrderService;
        this.messagingTemplate = messagingTemplate;
        this.dashboardService = dashboardService;
    }

    @Scheduled(fixedDelay = 5000)
    public void collectModbusData() {
        if (!isRunning) return;
        TCPMasterConnection connection = null;
        try {
            InetAddress address = InetAddress.getByName("localhost");
            connection = new TCPMasterConnection(address);
            connection.setPort(502);
            connection.connect();

            ReadMultipleRegistersRequest request = new ReadMultipleRegistersRequest(0, 3);
            ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
            transaction.setRequest(request);
            transaction.execute();

            ReadMultipleRegistersResponse response =
                    (ReadMultipleRegistersResponse) transaction.getResponse();

            int orderId   = response.getRegisterValue(0);
            int goodQty   = response.getRegisterValue(1);
            int defectQty = response.getRegisterValue(2);

            System.out.println("PLC 데이터 - orderId: " + orderId + ", goodQty: " + goodQty + ", defectQty: " + defectQty);

            if (orderId > 0 && (goodQty > 0 || defectQty > 0)) {
                WorkOrderDto workOrder = workOrderService.getWorkOrderById(orderId);

                if (workOrder == null) {
                    System.out.println("작업지시 ID " + orderId + " 없음!");
                    return;
                }

                // 이미 완료된 작업지시면 스킵
                if ("완료".equals(workOrder.getStatus())) {
                    System.out.println("작업지시 " + orderId + " 이미 완료됨. 수집 중지.");
                    isRunning = false;
                    return;
                }

                int targetQty = workOrder.getTargetQty();
                int totalGoodQty = productionResultService.getTotalGoodQtyByOrderId(orderId);

                // 목표수량 도달 체크
                if (totalGoodQty >= targetQty) {
                    // 작업지시 상태 완료로 변경
                    WorkOrderDto updateDto = new WorkOrderDto();
                    updateDto.setOrderId(orderId);
                    updateDto.setStatus("완료");
                    workOrderService.updateStatus(updateDto);

                    messagingTemplate.convertAndSend("/topic/dashboard", dashboardService.getDashboardData());
                    System.out.println("목표수량 달성! 작업지시 " + orderId + " 완료 처리");
                    isRunning = false;
                    return;
                }

                // 목표수량 초과 방지
                int remainQty = targetQty - totalGoodQty;
                int actualGoodQty = Math.min(goodQty, remainQty);

                ProductionResultDto dto = new ProductionResultDto();
                dto.setOrderId(orderId);
                dto.setGoodQty(actualGoodQty);
                dto.setDefectQty(defectQty);
                dto.setDefectType("해당없음");
                dto.setWorker("PLC자동수집");
                dto.setResultDate(LocalDate.now());

                productionResultService.insertResult(dto);

                messagingTemplate.convertAndSend("/topic/dashboard", dashboardService.getDashboardData());
                System.out.println("Modbus 데이터 수집 완료 - 작업지시: " + orderId +
                        ", 양품: " + actualGoodQty + ", 불량: " + defectQty);
            }

        } catch (Exception e) {
            System.out.println("Modbus 연결 실패 : " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
