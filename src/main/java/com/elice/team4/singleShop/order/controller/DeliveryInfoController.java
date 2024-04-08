package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.DeliveryInfoDto;
import com.elice.team4.singleShop.order.entity.DeliveryInfo;
import com.elice.team4.singleShop.order.service.DeliveryInfoService;
import com.elice.team4.singleShop.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryInfoController {

    private final OrderService orderService;

    @PutMapping("/{orderId}/delivery")
    public ResponseEntity<?> updateDeliveryInfo(@PathVariable Long orderId,
                                                @RequestBody @Valid DeliveryInfoDto deliveryInfoDto,
                                                BindingResult bindingResult) {
        // 데이터 바인딩 시 에러 유무 검사
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        // 배송 정보 수정
        DeliveryInfo updateDeliveryInfo = orderService.updateDeliveryInfo(orderId, deliveryInfoDto);
        return ResponseEntity.ok(updateDeliveryInfo);
    }
}