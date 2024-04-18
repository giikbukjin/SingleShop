package com.elice.team4.singleShop.product.domain;

import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.global.exception.NotEnoughStockException;
import com.elice.team4.singleShop.order.entity.OrderItem;
import com.elice.team4.singleShop.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private String summary;

    private String description;

    private String image;

    private int stock;

    private int price;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;


    @Column(name = "category_id", nullable = true)
    private Long categoryId;

    // ERD 관계 추가 (category)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
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
    private List<OrderItem> orderItems = new ArrayList<>();

//    @OneToMany
//    @JoinColumn(name = "cartitem_id")
//    private List<CartItem> cartItems = new ArrayList<>();


    public Product() {

    }

    public Product(String name, Long categoryId, String summary, String description, String image, int stock, int price) {
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.image = image;
        this.stock = stock;
        this.price = price;
        this.categoryId = categoryId;
    }

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
