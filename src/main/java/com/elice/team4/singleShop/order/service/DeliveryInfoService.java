package com.elice.team4.singleShop.order.service;

import com.elice.team4.singleShop.order.dto.DeliveryInfoDto;
import com.elice.team4.singleShop.order.entity.DeliveryInfo;
import com.elice.team4.singleShop.order.repository.DeliveryInfoRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryInfoService {

    private final DeliveryInfoRepository deliveryInfoRepository;

    // 새로운 배송 정보 생성 & 저장
    public DeliveryInfo createDeliveryInfo(DeliveryInfoDto deliveryInfoDto) {
        DeliveryInfo deliveryInfo = new DeliveryInfo(); // DeliveryInfo 객체 생성

        // Dto로 받은 정보를 DeliveryInfo 객체에 설정
        deliveryInfo.setReceiverName(deliveryInfoDto.getReceiverName());
        deliveryInfo.setReceiverPhoneNumber(deliveryInfoDto.getReceiverPhoneNumber());
        deliveryInfo.setPostalCode(deliveryInfoDto.getPostalCode());
        deliveryInfo.setAddress1(deliveryInfoDto.getAddress1());
        deliveryInfo.setAddress2(deliveryInfoDto.getAddress2());
        deliveryInfo.setDeliveryRequest(deliveryInfoDto.getDeliveryRequest());

        return deliveryInfoRepository.save(deliveryInfo); // 생성된 배송 정보 저장
    }
}