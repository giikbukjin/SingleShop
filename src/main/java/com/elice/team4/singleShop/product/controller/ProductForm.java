package com.elice.team4.singleShop.product.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {
    private Long id;

    private String name;

    private String category;

    private String summary;

    private String description;

    private String image;

    private int stock;

    private int price;
}
