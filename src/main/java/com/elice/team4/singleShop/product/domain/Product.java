package com.elice.team4.singleShop.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table (name = "product")
public class Product {

    @Id
    private int product_id;

    private String product_name;

    private String product_category;

    private String product_summary;

    private String product_description;

    private String product_image;

    private int product_stock;

    private int product_price;
}
