package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.OrderDto;
import com.elice.team4.singleShop.order.dto.OrderHistDto;
import com.elice.team4.singleShop.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 처리
    @PostMapping(value = "/order")
    public String order(@ModelAttribute("orderDto") @Valid OrderDto orderDto,
                        BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes) {
        // 데이터 바인딩 시 에러 있는지 검사
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            // 에러 정보를 StringBuilder에 추가
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            // 주문 조회 페이지로 리다이렉트하고 에러 메시지 전달
            redirectAttributes.addFlashAttribute("errorMessage", sb.toString());
            return "redirect:/order";
        }
        String email = principal.getName(); // 현재 로그인한 회원 이메일 정보 조회
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email); // 주문 정보, 회원 이메일 정보로 주문 로직 호출 -> 생성된 주문 번호 받아옴
        } catch (Exception e) {
            // 예외 발생 시 에러 메시지를 주문 조회 페이지로 전달
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/order";
        }
        // 주문이 성공적으로 이루어지면 주문 완료 페이지로 리다이렉트
        return "redirect:/order-complete"; // 수정된 부분
    }

    // 주문 내역 조회
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable(value = "page", required = false) Integer page, Principal principal, Model model) {
        Pageable pageable = PageRequest.of(page != null ? page : 0, 4);

        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(principal.getName(), pageable);

        model.addAttribute("order", orderHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "account-orders/account-orders";
    }

    // 주문 취소 처리
    @PostMapping("/order/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId, Principal principal, Model model) {
        // 주문 취소 권한 검사
        if (!orderService.validateOrder(orderId, principal.getName())) {
            model.addAttribute("errorMessage", "주문 취소 권한이 없습니다.");
            return "error-page"; // 에러 페이지로 이동
        }

        orderService.cancelOrder(orderId); // 주문 취소 로직 호출 -> 처리
        return "redirect:/order"; // 주문 내역 페이지로 리다이렉트
    }

    // 주문 완료 페이지로 이동
    @GetMapping("/order-complete")
    public String showOrderCompletePage() {
        return "order-complete/order-complete";
    }
}