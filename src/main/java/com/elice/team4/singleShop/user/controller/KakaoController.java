package com.elice.team4.singleShop.user.controller;

import com.elice.team4.singleShop.user.oauth.KakaoApi;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping
@Slf4j
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
    public String kakaoLogout(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String accessToken = "";
        if (request.getCookies() != null) {
            Optional<Cookie> tokenCookie = Arrays.stream(request.getCookies())
                    .filter(
                            cookie -> cookie.getName().equals("Authorization")
                    ).findFirst();

            if (tokenCookie.isPresent()) {
                String token = URLDecoder.decode(tokenCookie.get().getValue(), "UTF-8");
                if (token != null && token.startsWith("Bearer ")) {
                    accessToken = token.substring(7);
                }
            }
        }

        kakaoApi.kakaoLogout(accessToken);

        for (Cookie cookie : request.getCookies()) {
            String cookieName = cookie.getName();
            Cookie cookieToDelete = new Cookie(cookieName, null);
            cookieToDelete.setMaxAge(0);
            cookieToDelete.setPath("/");
            response.addCookie(cookieToDelete);
        }
        log.info("[kakaoLogout] 카카로 로그아웃 완료");
        return "redirect:/home";
    }
}
