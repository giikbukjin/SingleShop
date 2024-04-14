package com.elice.team4.singleShop.user.dto;

import com.elice.team4.singleShop.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModiRoleDto {

    private User.Role role;

}
