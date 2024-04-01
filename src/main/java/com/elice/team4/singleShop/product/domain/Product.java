package com.elice.team4.singleShop.product.domain;

import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.global.exception.NotEnoughStockException;
import com.elice.team4.singleShop.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private int id;

    private String name;

    private String summary;

    private String description;

    private String image;

    private int stock;

    private int price;

    // ERD 관계 추가 (category)
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // ERD 관계 추가 (user)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ERD 관계 추가 (cart)
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // ERD 관계 추가 (order_item)
    @OneToMany(mappedBy = "product")
    @JoinColumn(name = "order_item_id")
    private List<OrderItem> orderItem = new ArrayList<>();


    // *** 재고 수량 증가 ***
    public void addStock(int quantity) {
        this.stock += quantity;
    }

    // *** 재고 수량 감소 ***
    public void removeStock(int quantity) {
        int restStock = this.stock - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("Need More Stock");
        }
        this.stock = restStock;
    }
}
