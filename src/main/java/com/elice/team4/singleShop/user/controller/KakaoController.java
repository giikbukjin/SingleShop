package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.oauth.KakaoApi;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping
public class KakaoController {
    private final KakaoApi kakaoApi;

    public KakaoController(KakaoApi kakaoApi){
        this.kakaoApi = kakaoApi;
    }

    @RequestMapping("${kakao.redirect_shortUri}")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response){
        String accessToken = kakaoApi.getAccessToken(code, response);
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        String nickname = (String)userInfo.get("nickname");
        System.out.println("nickname = " + nickname);
        System.out.println("accessToken = " + accessToken);

        return "redirect:/home";
    }

    @RequestMapping("${kakao.logoutRedirect_shortUri}")
    public String kakaoLogout(@RequestParam String code, HttpServletResponse response){
        String accessToken = kakaoApi.getAccessToken(code, response);
        System.out.println(accessToken);
        kakaoApi.kakaoLogout(accessToken);
        return "redirect:/home";
    }
}
