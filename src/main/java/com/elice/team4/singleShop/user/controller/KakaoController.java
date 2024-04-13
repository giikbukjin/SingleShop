package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.oauth.KakaoApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @RequestMapping("/카카오 developer에서 발급받은 리다이렉트 url")
    public String kakaoLogin(@RequestParam String code){
        String accessToken = kakaoApi.getAccessToken(code);
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        String nickname = (String)userInfo.get("nickname");
        System.out.println("nickname = " + nickname);
        System.out.println("accessToken = " + accessToken);

        return "redirect:/home";
    }
}
