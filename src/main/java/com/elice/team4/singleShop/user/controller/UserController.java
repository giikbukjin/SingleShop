package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.dto.LogInRequestDto;
import com.elice.team4.singleShop.user.dto.LogInResultDto;
import com.elice.team4.singleShop.user.dto.SignUpRequestDto;
import com.elice.team4.singleShop.user.service.SignService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@Slf4j
public class UserController {
    private final SignService signService;

    public UserController(SignService signService) {
        this.signService = signService;
    }

    @GetMapping("/login")
    public String showLogInForm(Model model) {
        LogInRequestDto logInRequestDto = new LogInRequestDto();
        model.addAttribute("login", logInRequestDto);
        return "login/login";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        model.addAttribute("signup", signUpRequestDto);
        return "register/register";
    }

    @PostMapping(value = "/login")
    public String logIn(@Valid @ModelAttribute LogInRequestDto logInRequestDto, HttpServletResponse response)
            throws RuntimeException, UnsupportedEncodingException {
        String id = logInRequestDto.getEmail();
        String password = logInRequestDto.getPassword();
        log.info("[logIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", id);
        LogInResultDto logInResultDto = signService.logIn(id, password);

        if (logInResultDto.getCode() == 0) {
            log.info("[logIn] 정상적으로 로그인되었습니다. id : {}, token : {}", id,
                    logInResultDto.getToken());
        }
        var cookie = new Cookie("Authorization", URLEncoder.encode("Bearer " + logInResultDto.getToken(), StandardCharsets.UTF_8));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        if(String.valueOf(logInResultDto.getRole()).equals("ADMIN")){
            return "redirect:/admin";
        }
        return "redirect:/home";
    }


    @PostMapping(value = "/signup")
    public String signUp(@Valid @ModelAttribute SignUpRequestDto requestDto) {
        String name = requestDto.getName();
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();
        String role = "";

        log.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, role : {}", name,
                name, role);
        signService.signUp(name, password, email, role);

        log.info("[signUp] 회원가입을 완료했습니다. id : {}", name);
        return "redirect:/auth/signup";
    }

    @PostMapping(value = "/user/{id}/edit")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody SignUpRequestDto requestDto) {
        String name = requestDto.getName();
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();

        log.info("[updateUser] 회원 정보를 수정합니다. id : {}", id);
        signService.updateUser(id, name, password, email);

        log.info("[updateUser] 회원 정보 수정이 완료되었습니다. id : {}", id);
        return ResponseEntity.ok().body("회원 정보가 수정되었습니다.");
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/auth/login";
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
