package com.elice.team4.singleShop.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;

    private String name;

    private Long categoryId;

    private String summary;

    private String description;

    private String image;

    private int stock;

    private int price;
}