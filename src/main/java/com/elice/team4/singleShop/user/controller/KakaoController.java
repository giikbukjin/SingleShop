package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.oauth.KakaoApi;
import com.elice.team4.singleShop.user.service.SignService;
import com.elice.team4.singleShop.user.service.SignServiceImpl;
import com.elice.team4.singleShop.user.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping
@Slf4j
public class KakaoController {
    private final KakaoApi kakaoApi;
    private final UserDetailsServiceImpl userDetailsService;
    private final SignService signService;
    public KakaoController(KakaoApi kakaoApi, UserDetailsServiceImpl userDetailsService, SignService signService){
        this.kakaoApi = kakaoApi;
        this.userDetailsService = userDetailsService;
        this.signService = signService;
    }

    @RequestMapping("${kakao.redirect_shortUri}")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        String accessToken = kakaoApi.getAccessToken(code, response);
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        String nickname = (String) userInfo.get("nickname");
        log.info("[kakaoLogin] 카카오 닉네임: {}", nickname);
        log.info("[kakaoLogin] 카카오 로그인 액세스 토큰: {}", accessToken);
        if(!userDetailsService.checkUserByName(nickname)){
            signService.kakaoSignup(nickname);
        }

        return "redirect:/home";
    }
}
