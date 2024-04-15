package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.oauth.KakaoApi;
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
    public KakaoController(KakaoApi kakaoApi){
        this.kakaoApi = kakaoApi;
    }

    @RequestMapping("${kakao.redirect_shortUri}")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        String accessToken = kakaoApi.getAccessToken(code, response);
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        String nickname = (String) userInfo.get("nickname");
        log.info("[kakaoLogin] 카카오 닉네임: {}", nickname);
        log.info("[kakaoLogin] 카카오 로그인 액세스 토큰: {}", accessToken);

        return "redirect:/home";
    }
}
