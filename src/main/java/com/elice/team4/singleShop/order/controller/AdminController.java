package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.OrderDto;
import com.elice.team4.singleShop.order.dto.OrderHistDto;
import com.elice.team4.singleShop.order.entity.Order;
import com.elice.team4.singleShop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminController {

    private final OrderService orderService;

    // 주문 내역 조회 페이지로 이동
    @GetMapping("/admin/orders")
    public String getAllOrders(Model model) {
        // 모든 주문 내역 조회
        List<OrderDto> orders = orderService.getAllOrders();
        // 주문 데이터를 모델에 추가하여 HTML 템플릿으로 전달
        model.addAttribute("orders", orders);
        // 주문 내역 조회 페이지로 이동
        return "admin-orders/admin-orders"; // admin-orders.html 템플릿을 렌더링
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<OrderDto>> getUserOrders(@PathVariable String email) {
        List<OrderDto> orders = orderService.getUserOrders(email);

        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orders);
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
