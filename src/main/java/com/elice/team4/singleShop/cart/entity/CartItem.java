package com.elice.team4.singleShop.cart.entity;

import com.elice.team4.singleShop.product.domain.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_id")
    private Product product;

    private int count; // 카트에 담긴 상품 개수


    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDate.now();
    }

    public static CartItem createCartItem(Cart cart,Product product,int count){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setCount(count);

        return cartItem;
    }

    public void addCount(int count){
        this.count += count;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "Id=" + Id +
                ", cart=" + (cart != null ? cart.getId() : null) +
                ", product=" + (product != null ? product.getId() : null) +
                ", count=" + count +
                ", createDate=" + createDate +
                '}';
    }
}