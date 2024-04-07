package com.elice.team4.singleShop.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "delivery_info")
public class DeliveryInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String receiverName; // 수령인 이름

    private String receiverPhoneNumber; // 연락처

    private String postalCode; // 우편번호

    private String address1; // 주소

    private String address2; // 상세 주소

    private String deliveryRequest; // 배송 요청 사항

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}