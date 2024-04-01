package com.elice.team4.singleShop.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    //@NotNull(message = "상품 아이디는 필수값입니다.")
    private Long productId;

    private int count;
}