package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.dto.LogInRequestDto;
import com.elice.team4.singleShop.user.dto.LogInResultDto;
import com.elice.team4.singleShop.user.dto.SignUpRequestDto;
import com.elice.team4.singleShop.user.dto.SignUpResultDto;
import com.elice.team4.singleShop.user.service.SignService;
import com.elice.team4.singleShop.user.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody SignUpRequestDto requestDto) {
        String name = requestDto.getName();
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();

        log.info("[updateUser] 회원 정보를 수정합니다. id : {}", id);
        signService.updateUser(id, name, password, email);

        log.info("[updateUser] 회원 정보 수정이 완료되었습니다. id : {}", id);
        return ResponseEntity.ok().body("회원 정보가 수정되었습니다.");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        log.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

}
