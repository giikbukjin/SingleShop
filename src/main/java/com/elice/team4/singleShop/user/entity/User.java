package com.elice.team4.singleShop.user.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @CreatedDate
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("CONSUMER") //TODO: 코치님에게 질문하기
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
}
