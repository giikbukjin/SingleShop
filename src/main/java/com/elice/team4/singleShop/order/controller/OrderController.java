package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.OrderDto;
import com.elice.team4.singleShop.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 처리
    /*@PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order (@RequestBody @Valid OrderDto orderDto,
                                               BindingResult bindingResult, Principal principal) {
        // 데이터 바인딩 시 에러 있는지 검사
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            // 에러 정보를 StringBuilder에 추가
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST); // 에러 정보와 BAD_REQUEST 상태코드 반환
        }
        String email = principal.getName(); // 현재 로그인한 회원 이메일 정보 조회
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email); // 주문 정보, 회원 이메일 정보로 주문 로직 호출 -> 생성된 주문 번호 받아옴
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST); // 예외 벌생 시 예외 메시지와 BAD_REQUEST 상태코드 반환
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK); // 생성된 주문 번호와 요청 성공 HTTP 응답 상태 코드
    }

    // 주문 취소 처리
    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder
            (@PathVariable("orderId") Long orderId, Principal principal) {
        // 주문 취소 권한 검사
        if (!orderService.validateOrder(orderId, principal.getName())) {
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN); // 권한이 없는 경우 FORBIDDEN 반환
        }

        orderService.cancelOrder(orderId); // 주문 취소 로직 호출 -> 처리
        return new ResponseEntity<Long>(orderId, HttpStatus.OK); // 주문 번호와 OK 반환
    }*/

}