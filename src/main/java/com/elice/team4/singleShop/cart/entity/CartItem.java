package com.elice.team4.singleShop.cart.entity;

import com.elice.team4.singleShop.product.domain.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cart_id")
    private Cart cart;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id")
    private Product product;

    private int count; // 카트에 담긴 상품 개수


    @Column(name = "createdDateTime", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column(name = "updatedDateTime")
    @UpdateTimestamp
    private LocalDateTime updatedDateTime;

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
                ", createDate=" + createdDateTime +
                '}';
    }
}