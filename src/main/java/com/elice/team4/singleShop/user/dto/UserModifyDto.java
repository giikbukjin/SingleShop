package com.elice.team4.singleShop.user.dto;

import com.elice.team4.singleShop.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModifyDto {
    private Long id;
    private String name;
    private String password1;
    private String password2;
    private String address;
    private String phoneNumber;
}
