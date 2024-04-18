package com.elice.team4.singleShop.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductDto {
    private Long id;

    private String name;

    private Long categoryId;

    private String summary;

    private String description;

    private String imgName;
    private String imgPath;

    private int stock;

    private int price;
}