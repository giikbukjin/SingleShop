package com.elice.team4.singleShop.order.controller;

import com.elice.team4.singleShop.order.dto.DeliveryInfoDto;
import com.elice.team4.singleShop.order.entity.DeliveryInfo;
import com.elice.team4.singleShop.order.service.DeliveryInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{deliveryInfoId}")
    public ResponseEntity<?> updateDeliveryInfo(@PathVariable Long deliveryInfoId,
                                                @RequestBody @Valid DeliveryInfoDto deliveryInfoDto,
                                                BindingResult bindingResult) {
        // 데이터 바인딩 시 에러 유무 검사
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        // 배송 정보 수정
        DeliveryInfo updateDeliveryInfo = deliveryInfoService.updateDeliveryInfo(deliveryInfoId, deliveryInfoDto);
        return ResponseEntity.ok(updateDeliveryInfo);
    }
}