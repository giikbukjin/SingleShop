package com.elice.team4.singleShop.cart.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cart {

    private Long cartId;

    private Long productId;     // 1대다

    private Long userId;        //1대1



}
