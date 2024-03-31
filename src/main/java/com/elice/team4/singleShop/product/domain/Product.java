package com.elice.team4.singleShop.product.domain;

import com.elice.team4.singleShop.global.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    // todo: ERD 관계 추가 (카테고리)
    private String category;

    private String summary;

    private String description;

    private String image;

    private int stock;

    private int price;


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
