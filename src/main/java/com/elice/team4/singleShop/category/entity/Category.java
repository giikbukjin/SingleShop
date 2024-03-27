package com.elice.team4.singleShop.category.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    private String categoryContent;

    private String categoryTheme;

    // TODO: Product 다대일 관계 필드 선언

    public Category(String categoryName, String categoryContent, String categoryTheme) {
        this.categoryName = categoryName;
        this.categoryContent = categoryContent;
        this.categoryTheme = categoryTheme;
    }
}
