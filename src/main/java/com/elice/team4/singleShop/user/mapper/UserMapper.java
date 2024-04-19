package com.elice.team4.singleShop.user.mapper;

import com.elice.team4.singleShop.user.dto.UserDto;
import com.elice.team4.singleShop.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User UserDtoToUser(UserDto userDto);
}
