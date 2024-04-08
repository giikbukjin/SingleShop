package com.elice.team4.singleShop.user.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJwtFromRequest(request); // TODO: 왜 계속 null 반환인지 여쭤보기
        log.info("[doFilterInternal] token 값 추출 완료, token: {}", token);

        log.info("[doFilterInternal] token 값 유효성 체크 시작");
        if(token != null && jwtTokenProvider.validateToken(token)){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("[doFilterInternal] token 값 유효성 체크 완료");
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) throws UnsupportedEncodingException {
        if (request.getCookies() != null) {
            Optional<Cookie> authorizationCookie = Arrays.stream(request.getCookies())
                    .filter(
                            cookie -> cookie.getName().equals("Authorization")
                    ).findFirst();

            if (authorizationCookie.isPresent()) {
                String token = URLDecoder.decode(authorizationCookie.get().getValue(), "UTF-8");
                if (token != null && token.startsWith("Bearer ")) {
                    return token.substring(7);
                }
                log.info(token);
            }
        }
        return null;
    }
}
