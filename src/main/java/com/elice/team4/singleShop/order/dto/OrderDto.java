package com.elice.team4.singleShop.order.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Min;
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
}