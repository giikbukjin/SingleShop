package com.elice.team4.singleShop.order.service;

import com.elice.team4.singleShop.order.dto.DeliveryInfoDto;
import com.elice.team4.singleShop.order.entity.DeliveryInfo;
import com.elice.team4.singleShop.order.repository.DeliveryInfoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryInfoService {

    private final DeliveryInfoRepository deliveryInfoRepository;

    public DeliveryInfo createDeliveryInfo(DeliveryInfoDto deliveryInfoDto) {
        DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setReceiverName(deliveryInfoDto.getReceiverName());
        deliveryInfo.setReceiverPhoneNumber(deliveryInfoDto.getReceiverPhoneNumber());
        deliveryInfo.setPostalCode(deliveryInfoDto.getPostalCode());
        deliveryInfo.setAddress1(deliveryInfoDto.getAddress1());
        deliveryInfo.setAddress2(deliveryInfoDto.getAddress2());
        deliveryInfo.setDeliveryRequest(deliveryInfoDto.getDeliveryRequest());

        return deliveryInfoRepository.save(deliveryInfo);
    }
}