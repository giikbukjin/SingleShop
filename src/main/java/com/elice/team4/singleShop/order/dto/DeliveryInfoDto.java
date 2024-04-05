package com.elice.team4.singleShop.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryInfoDto {

    @NotBlank(message = "이름은 필수입니다.")
    private String receiverName;

    @NotBlank(message = "연락처는 필수입니다.")
    private String receiverPhoneNumber;

    @NotBlank(message = "우편번호는 필수입니다.")
    private String postalCode;

    @NotBlank(message = "주소는 필수입니다.")
    private String address1;

    private String address2;

    private String deliveryRequest;
}