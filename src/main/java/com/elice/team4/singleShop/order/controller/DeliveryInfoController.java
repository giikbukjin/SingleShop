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
    public ResponseEntity<?> createDeliveryInfo(@RequestBody @Valid DeliveryInfoDto deliveryInfoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        DeliveryInfo savedDeliveryInfo = deliveryInfoService.createDeliveryInfo(deliveryInfoDto);
        return ResponseEntity.ok(savedDeliveryInfo);
    }
}