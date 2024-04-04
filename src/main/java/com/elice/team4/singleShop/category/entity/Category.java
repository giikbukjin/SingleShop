package com.elice.team4.singleShop.category.entity;

import com.elice.team4.singleShop.product.domain.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "카테고리 이름은 공백일 수 없습니다.")
    @Column(nullable = false, unique = true, length = 50)
    private String categoryName;

    @NotBlank(message = "카테고리 설명은 공백일 수 없습니다.")
    @Column(nullable = false, length = 100)
    private String categoryContent;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "id")
    private List<Product> products = new ArrayList<>();

    public Category(String categoryName, String categoryContent) {
        this.categoryName = categoryName;
        this.categoryContent = categoryContent;
    }

    public Category(String categoryName) {
    }

    public void update(Category updatedCategory) {
        if (updatedCategory.getCategoryName() != null) {
            this.categoryName = updatedCategory.getCategoryName();
        }
        if (updatedCategory.getCategoryContent() != null) {
            this.categoryContent = updatedCategory.getCategoryContent();
        }
    }
}
