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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;        //1대1

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
