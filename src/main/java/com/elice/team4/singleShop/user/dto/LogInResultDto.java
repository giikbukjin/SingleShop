package com.elice.team4.singleShop.user.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogInResultDto extends SignUpResultDto{
    private String token;

    @Builder
    public LogInResultDto(boolean success, int code, String message, String token) {
        super(success, code, message);
        this.token = token;
    }
}
