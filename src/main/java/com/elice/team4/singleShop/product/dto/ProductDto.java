package com.elice.team4.singleShop.product.dto;

import com.elice.team4.singleShop.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDto {
    private Long id;

    private String name;

    //private Category category;

    private String summary;

    private String description;

    private String image;

    private int stock;

    private int price;
}