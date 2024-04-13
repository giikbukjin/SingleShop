package com.elice.team4.singleShop.user.dto;

import com.elice.team4.singleShop.user.entity.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogInResultDto extends SignUpResultDto{
    private String token;
    private String refreshToken;
    private User.Role role;

    @Builder //확인하기
    public LogInResultDto(boolean success, int code, String message, String token, String refreshToken, User.Role role) {
        super(success, code, message);
        this.token = token;
        this.refreshToken = refreshToken;
        this.role = role;
    }
}
