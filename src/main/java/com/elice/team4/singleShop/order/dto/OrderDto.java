package com.elice.team4.singleShop.order.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    @NotNull(message = "상품 아이디는 필수값입니다.")
    private Long productId;

    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    private int count;

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