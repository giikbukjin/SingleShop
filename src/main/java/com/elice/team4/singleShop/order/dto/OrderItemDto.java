package com.elice.team4.singleShop.order.dto;

import com.elice.team4.singleShop.order.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

// 조회한 주문 데이터를 화면에 전송, 주문 상품 정보, 구매 이력
@Getter @Setter
public class OrderItemDto {

    public OrderItemDto(OrderItem orderItem, String imageUrl) {
        this.itemName = orderItem.getProduct().getName();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.image = image;
    }

    private String itemName; // 상품명

    private int count; // 주문 수량

    private int orderPrice; // 주문 금액

    private String image; // 상품 이미지 경로

    private String receiverName;

    private String receiverPhoneNumber;

    private String postalCode;

    private String address1;

    private String address2;

    private String deliveryRequest;
}