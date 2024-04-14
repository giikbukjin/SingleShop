package com.elice.team4.singleShop.order.entity;

import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "orders_item")
//@MappedSuperclass
//@EntityListeners(value = {AuditingEntityListener.class})
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격

    private int count; // 수량

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime; // 등록 시간

    @LastModifiedDate
    private LocalDateTime updateTime; // 수정 시간

    @CreatedBy
    @Column(updatable = false)
    private String createdBy; // 등록자

    @LastModifiedDate
    private String modifiedBy; // 수정자

    private String receiverName; // 수령인 이름

    private String receiverPhoneNumber; // 연락처

    private String postalCode; // 우편번호

    private String address1; // 주소

    private String address2; // 상세 주소

    private String deliveryRequest; // 배송 요청 사항
    
    public static OrderItem createOrderItem(Product product, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product); // 주문할 상품 세팅
        orderItem.setCount(count); // 주문 수량 세팅
        orderItem.setOrderPrice(product.getPrice()); // 상품 가격을 주문 가격으로 세팅
        
        product.removeStock(count); // 주문 수량만큼 재고 수량 감소
        return orderItem;
    }

    public int getTotalPrice() {
        return orderPrice * count; // 총 가격 계산
    }

    public void cancel() {
        this.getProduct().addStock(count); // 주문 취소 시 상품 재고 추가
    }

}