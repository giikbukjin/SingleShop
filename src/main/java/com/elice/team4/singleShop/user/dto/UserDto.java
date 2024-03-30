package com.elice.team4.singleShop.user.dto;

import com.elice.team4.singleShop.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String password;
    private String email;
    private User.Role role;
}
