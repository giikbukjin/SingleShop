package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.DeliveryInfoDto;
import com.elice.team4.singleShop.order.entity.DeliveryInfo;
import com.elice.team4.singleShop.order.service.DeliveryInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryInfoController {

    private final DeliveryInfoService deliveryInfoService;

    @PostMapping
    public ResponseEntity<?> createDeliveryInfo(@RequestBody @Valid DeliveryInfoDto deliveryInfoDto,
                                                BindingResult bindingResult) { // 요청 본문을 Dto 객체로 매핑, 전달된 Dto 객체 유효성 검사
        // 유효성 검사 결과 오류 있는 경우
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        // 배송 정보 생성 & 저장
        DeliveryInfo savedDeliveryInfo = deliveryInfoService.createDeliveryInfo(deliveryInfoDto);
        return ResponseEntity.ok(savedDeliveryInfo);
    }
}