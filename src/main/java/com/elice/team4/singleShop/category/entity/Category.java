package com.elice.team4.singleShop.category.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String categoryName;

    @Column(nullable = false, length = 100)
    private String categoryContent;

    // TODO: Product 다대일 관계 필드 선언
//    @OneToMany(mappedBy = "product")
//    final private List<Product> products = new ArrayList<>();

    public Category(String categoryName, String categoryContent) {
        this.categoryName = categoryName;
        this.categoryContent = categoryContent;
    }
}
