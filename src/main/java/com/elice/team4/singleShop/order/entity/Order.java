package com.elice.team4.singleShop.order.entity;

import com.elice.team4.singleShop.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
//@MappedSuperclass
//@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // order_id

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 사용자는 여러 개의 주문내역 존재

    public enum OrderStatus {
        ORDER, CANCEL
    }

    public void addOrderItem(OrderItem orderItem) { // 주문 상품 정보 담기, orderItem 객체를 order 객체의 orderItems에 추가
        orderItems.add(orderItem);
        orderItem.setOrder(this); // orderItem 객체에도 order 세팅
    }

    public static Order createOrder(User user, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setUser(user); // 상품 주문한 회원 정보 세팅
        for (OrderItem orderItem : orderItemList) { // 여러 개의 상품 주문 가능
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER); // 주문 상태를 "ORDER"로 세팅
        order.setOrderDate(LocalDateTime.now()); // 현재 시간을 주문 시간으로 세팅
        return order;
    }

    // 총 주문 금액
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    // 주문 취소
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
}
