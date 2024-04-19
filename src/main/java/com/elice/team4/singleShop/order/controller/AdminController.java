package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.OrderDto;
import com.elice.team4.singleShop.order.dto.OrdersDto;
import com.elice.team4.singleShop.order.entity.Order;
import com.elice.team4.singleShop.order.repository.OrdersRepository;
import com.elice.team4.singleShop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
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
    private final OrdersRepository ordersRepository;

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

    /*@GetMapping("/user/{email}")
    public String getUserOrders(@PathVariable("email") String email, Model model) {
        List<OrderDto> orders = orderService.getUserOrders(email);

        if (orders.isEmpty()) {
            // 주문 내역이 없는 경우
            return "error"; // 주문 내역이 없음을 알리는 HTML 템플릿을 렌더링
        }

        // 주문 내역이 있는 경우 해당 내역을 모델에 추가하여 HTML 템플릿으로 전달
        model.addAttribute("orders", orders);
        return "account-orders/account-orders"; // user-orders.html 템플릿을 렌더링
    }*/

    /*@PatchMapping("{OrderId}/status")
    public String updateOrderStatus(@PathVariable("orderId") Long orderId,
                                    @RequestParam Order.OrderStatus newStatus) {
        // 배송 상태 수정하고 결과 받아옴
        boolean updated = orderService.updateOrderStatus(orderId, newStatus);
        if (updated) {
            // 배송 수정 성공 시
            return "redirect:/admin/orders"; // 관리자용 주문 내역 페이지로 리다이렉트
        } else {
            // 주문 존재하지 않거나 수정 실패 시
            return "error"; // 에러 페이지로 리다이렉트
        }
    }*/

    @DeleteMapping("{orderId}")
    public String deleteOrder(@PathVariable("orderId") Long orderId) {
        // 주문 내역 삭제하고 결과 받아옴
        boolean deleted = orderService.deleteOrder(orderId);
        if (deleted) {
            // 주문 내역 삭제 성공 시
            return "redirect:/admin/orders"; // 관리자용 주문 내역 페이지로 리다이렉트
        } else {
            // 주문 내역 존재하지 않거나 삭제 실패 시
            return "error"; // 에러 페이지로 리다이렉트
        }
    }

    @GetMapping("/admin/api/orders")
    @ResponseBody
    public ResponseEntity<List<OrdersDto>> getOrdersList() {
        List<OrdersDto> ordersDtoList = ordersRepository.findAll();

        return ResponseEntity.ok(ordersDtoList);
    }

    @PatchMapping("/admin/api/orders/{id}")
    public ResponseEntity<OrdersDto> updateStatus(@PathVariable(name = "id") Long id,
                                                  @RequestBody OrdersDto ordersDto) {
        OrdersDto ordersDto1 = ordersRepository.findById(id).orElseThrow();
        ordersDto1.setStatus(ordersDto.getStatus());
        ordersRepository.save(ordersDto1);
        return ResponseEntity.ok(ordersDto1);
    }

    @DeleteMapping("/admin/api/orders/{id}")
    public ResponseEntity deleteStatus(@PathVariable(name = "id") Long id) {
        ordersRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
