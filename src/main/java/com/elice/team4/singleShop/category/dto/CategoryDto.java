package com.elice.team4.singleShop.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {

    @NotNull
    @NotBlank(message = "카테고리 이름은 공백일 수 없습니다.")
    @Size(min = 1, max = 20)
    private String categoryName;

    @NotNull
    @NotBlank(message = "카테고리 설명은 공백일 수 없습니다.")
    @Size(min = 1, max = 200)
    private String categoryContent;
}
