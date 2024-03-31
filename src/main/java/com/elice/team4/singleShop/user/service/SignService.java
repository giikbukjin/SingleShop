package com.elice.team4.singleShop.user.service;

import com.elice.team4.singleShop.user.dto.LogInResultDto;
import com.elice.team4.singleShop.user.dto.SignUpResultDto;

public interface SignService {
    SignUpResultDto signUp(String name, String password, String email, String role);

    LogInResultDto logIn(String name, String password) throws RuntimeException;
}
