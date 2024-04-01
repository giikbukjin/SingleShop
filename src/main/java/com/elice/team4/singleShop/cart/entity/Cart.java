package com.elice.team4.singleShop.cart.entity;

import com.elice.team4.singleShop.order.entity.Order;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id")
    private List<Product> products = new ArrayList<>();     // 1대다

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;        //1대1

    @OneToMany
    @JoinColumn(name= "order_item")
    private Order order;        //카트와 오더 맵핑 괜찮나?

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate;

    @PrePersist
    public void createDate(){
        this.createDate = LocalDate.now();
    }

    public static Cart createCart(User user){
        Cart cart = new Cart();
        cart.user = user;

        return cart;
    }


}
