package com.elice.team4.singleShop.global.controller;

import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AdminCheckController {

    private final JwtTokenProvider jwtTokenProvider;
    @GetMapping("/api/users/admin-check")
    public ResponseEntity<String> apiCheckAdmin(
            @CookieValue(value = "Authorization") String value
    ) {
        String token = value.substring(7);
        log.info("토큰 값 : {}",token);

        UserDetails userDetailsInfo = jwtTokenProvider.getUserDetailsInfo(token);
        User userFindByName = jwtTokenProvider.getUserInfo(userDetailsInfo.getUsername());

        if(!String.valueOf(userFindByName.getRole()).equals("ADMIN")){
            return ResponseEntity.ok("{\"status\": \"fail\"}");
        }

        String ok = "{\"status\": \"success\"}";
        return ResponseEntity.ok(ok);
    }

    @GetMapping("/admin/api/users/admin-check")
    public ResponseEntity<String> adminCheckAdmin(
            @CookieValue(value = "Authorization") String value
    ) {
        String token = value.substring(7);
        log.info("토큰 값 : {}",token);

        UserDetails userDetailsInfo = jwtTokenProvider.getUserDetailsInfo(token);
        User userFindByName = jwtTokenProvider.getUserInfo(userDetailsInfo.getUsername());

        if(!String.valueOf(userFindByName.getRole()).equals("ADMIN")){
            return ResponseEntity.ok("{\"status\": \"fail\"}");
        }

        String ok = "{\"status\": \"success\"}";
        return ResponseEntity.ok(ok);
    }

    @GetMapping("/account/api/users/admin-check")
    public ResponseEntity<String> accountCheckAdmin(
            @CookieValue(value = "Authorization") String value
    ) {
        String token = value.substring(7);
        log.info("토큰 값 : {}",token);

        UserDetails userDetailsInfo = jwtTokenProvider.getUserDetailsInfo(token);
        User userFindByName = jwtTokenProvider.getUserInfo(userDetailsInfo.getUsername());

        if(!String.valueOf(userFindByName.getRole()).equals("ADMIN")){
            return ResponseEntity.ok("{\"status\": \"fail\"}");
        }

        String ok = "{\"status\": \"success\"}";
        return ResponseEntity.ok(ok);
    }

    @GetMapping("/account/security/api/users/admin-check")
    public ResponseEntity<String> accountSecurityCheckAdmin(
            @CookieValue(value = "Authorization") String value
    ) {
        String token = value.substring(7);
        log.info("토큰 값 : {}",token);

        UserDetails userDetailsInfo = jwtTokenProvider.getUserDetailsInfo(token);
        User userFindByName = jwtTokenProvider.getUserInfo(userDetailsInfo.getUsername());

        if(!String.valueOf(userFindByName.getRole()).equals("ADMIN")){
            return ResponseEntity.ok("{\"status\": \"fail\"}");
        }

        String ok = "{\"status\": \"success\"}";
        return ResponseEntity.ok(ok);
    }
}
