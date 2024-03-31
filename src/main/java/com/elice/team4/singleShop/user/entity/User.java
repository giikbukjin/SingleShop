package com.elice.team4.singleShop.user.entity;

import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.order.entity.Order;
import com.elice.team4.singleShop.product.domain.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

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


//    @OneToMany(mappedBy = "user") TODO: 진서님 Product엔티티에 User컬럼 추가 요망
//    private Product product;

    @OneToOne(mappedBy = "user")
    private Cart cart;

//    @OneToMany(mappedBy = "user") TODO: 민상님 Order엔티티 주석 제거 요망
//    private Order order;

    public enum Role{
        CONSUMER,
        SELLER,
        ADMIN
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role.toString()));
        return authorities;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.name;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return false;
    }

    public void update(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
