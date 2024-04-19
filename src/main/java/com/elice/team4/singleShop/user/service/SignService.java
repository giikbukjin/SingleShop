package com.elice.team4.singleShop.user.service;

import com.elice.team4.singleShop.user.dto.LogInResultDto;
import com.elice.team4.singleShop.user.dto.SignUpResultDto;
import jakarta.servlet.http.HttpServletResponse;

public interface SignService {
    SignUpResultDto signUp(String name, String password, String email, String role);

    LogInResultDto logIn(String name, String password) throws RuntimeException;

    void updateUser(Long id, String name, String password, String email);

    void kakaoSignup(String id, String nickname);

    void kakaoLogin(String nickname, HttpServletResponse response);
}
