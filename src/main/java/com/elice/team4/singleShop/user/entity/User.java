package com.elice.team4.singleShop.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "아이디는 비어있을 수 없습니다.")
    @Size(min = 8, max = 15, message = "아이디는 8 ~ 15자 이어야 합니다.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8 ~ 15자 이어야 합니다.")
    @Column(nullable = false)
    private String password;

    @Email(message = "이메일 양식을 확인해주세요.")
    @NotBlank(message = "이메일은 비어있을 수 없습니다.")
    @Column(nullable = false)
    private String email;

    @CreatedDate
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("CONSUMER") //TODO: 코치님에게 질문하기(Bean Validation 의존성 추가)
    private Role role;

//    TODO: 매핑필요
//    @OneToMany(mappedBy = "user")
//    private Product product;

//    TODO: 매핑필요
//    @OneToOne(mappedBy = "user")
//    private Cart cart;

//    TODO: 매핑필요
//    @OneToMany(mappedBy = "user")
//    private Order order;
    public enum Role{
        CONSUMER,
        SELLER,
        ADMIN
    }

    public void update(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
