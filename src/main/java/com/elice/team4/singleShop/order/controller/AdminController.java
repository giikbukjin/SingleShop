package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.OrderDto;
import com.elice.team4.singleShop.order.entity.Order;
import com.elice.team4.singleShop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminController {

    private final OrderService orderService;

    // 주문 내역 조회
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders(); // 모든 주문 내역 조회
        return ResponseEntity.ok(orders); // 조회 주문 내역 반환
    }

    // 배송 상태 수정
    @PatchMapping("{OrderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId,
                                                    @RequestParam Order.OrderStatus newStatus) {
        // 배송 상태 수정하고 결과 받아옴
        boolean updated = orderService.updateOrderStatus(orderId, newStatus);
        if (updated) {
            return ResponseEntity.ok("배송 상태가 수정되었습니다."); // 배송 수정 성공
        } else {
            return ResponseEntity.badRequest().body("배송 상태 수정에 실패하였습니다."); // 주문 존재하지 않거나 수정 실패
        }
    }

    // 주문 내역 삭제
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        // 주문 내역 삭제하고 결과 받아옴
        boolean deleted = orderService.deleteOrder(orderId);
        if (deleted) {
            return ResponseEntity.ok("주문 내역이 삭제되었습니다."); // 주문 내역 삭제 성공
        } else {
            return ResponseEntity.badRequest().body("주문 내역 삭제에 실패하였습니다."); // 주문 내역 존재하지 않거나 삭제 실패
        }
    }
}