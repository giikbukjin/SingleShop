package com.elice.team4.singleShop.user.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpResultDto {
    private boolean success;
    private int code;
    private String message;
}
