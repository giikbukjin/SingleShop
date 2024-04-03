package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.dto.LogInRequestDto;
import com.elice.team4.singleShop.user.dto.LogInResultDto;
import com.elice.team4.singleShop.user.dto.SignUpRequestDto;
import com.elice.team4.singleShop.user.dto.SignUpResultDto;
import com.elice.team4.singleShop.user.service.SignService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
    private final SignService signService;

    public UserController(SignService signService) {
        this.signService = signService;
    }

    @PostMapping(value = "/log-in")
    public LogInResultDto logIn(@Valid @RequestBody LogInRequestDto logInRequestDto)
            throws RuntimeException {
        String name = logInRequestDto.getName();
        String password = logInRequestDto.getPassword();
        log.info("[logIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", name);
        LogInResultDto logInResultDto = signService.logIn(name, password);

        if (logInResultDto.getCode() == 0) {
            log.info("[logIn] 정상적으로 로그인되었습니다. id : {}, token : {}", name,
                    logInResultDto.getToken());
        }
        return logInResultDto;
    }

    @PostMapping(value = "/sign-up")
    public SignUpResultDto signUp(@Valid @RequestBody SignUpRequestDto requestDto) {
        String name = requestDto.getName();
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();
        String role = requestDto.getRole();

        log.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, role : {}", name,
                name, role);
        SignUpResultDto signUpResultDto = signService.signUp(name, password, email, role);

        log.info("[signUp] 회원가입을 완료했습니다. id : {}", name);
        return signUpResultDto;
    }

}
