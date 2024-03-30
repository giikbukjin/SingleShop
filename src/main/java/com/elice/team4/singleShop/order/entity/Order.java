package com.elice.team4.singleShop.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

//import javax.annotation.processing.Generated;
import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;

@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // order_id

    private String status; // 주문처리현황

    private int price; // 총 금액

    //@ManyToOne
    //@JoinColumn(name = 'user_id')
    //private User user; // 사용자는 여러 개의 주문내역 존재

    //@OneToOne
    //@JoinColumn(name = "cart_id")
    //private Cart cart;

    //@OneToMany
    //private List<Product> products = new ArrayList<>(); // 주문은 여러 개의 상품 가질 수 있음

    /*@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDate createDate; // 주문 날짜

    @PrePersist
    public void createDate() {
        this.createDate = LocalDate.now();
    } // 현재 날짜로 설정해 생성일 기록 */

    /*public void addProduct(Product product) {
        products.add(product); // 리스트에 상품 추가
        product.setOrder(this); // 상품에 주문 설정
    }

    // 사용자가 여러 제품 주문
    public static Order creatOrder(User user, List<Product> productList) {
        Order order = new Order(); // 새로운 Order 객체 생성
        order.setUser(user); // 사용자 설정

        // productList에 있는 각 Product를 주문에 추가
        for (Product product : productList) {
            order.addProduct(product);
        }
        order.setCreateDate(order.createDate); // 생성 날짜 설정
        return order;
    }

    // 사용자가 단일 제품 주문
    public static Order createOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setCreateDate(order.createDate);
        return order;
    }

    public int getPrice() {
        int price = 0;
        for(Product product : products) {
            price += product.getPrice();
        }
        return price;
    }*/
}
