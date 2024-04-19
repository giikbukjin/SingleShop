package com.elice.team4.singleShop.order.dto;

import com.elice.team4.singleShop.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class ProductOrderDto {

    private Long id;
    private int price;
    private String title;
    private String detailDescription;
    private String menufacturer;
    private String imageKey;

    public ProductOrderDto(Product product) {
        this.id = product.getId();
        this.price = product.getPrice();
        this.title = product.getName();
        this.detailDescription = product.getDescription();
        this.menufacturer = product.getCategory().getCategoryName();
        this.imageKey = product.getImage();

    }
}
