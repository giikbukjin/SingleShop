package com.elice.team4.singleShop.global.config;

import com.elice.team4.singleShop.user.jwt.JwtTokenFilter;
import com.elice.team4.singleShop.user.jwt.JwtTokenProvider;
import com.elice.team4.singleShop.user.oauth.KakaoApi;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    // JwtTokenProvider : AuthenticationProvider, 인증 전 authentication -> 인증 후 authentication 으로 바꿔 주는 것
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoApi kakaoApi;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, KakaoApi kakaoApi) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.kakaoApi = kakaoApi;
    }

    // 정적 리소스를 이용할 때, 권한을 무시하는 코드 ( 다만, 디렉토리 구조까지 설정 되어 있음 )
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 패스워드 부호화
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 필터체인?
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 무상태성 설정
        http.setSharedObject(SessionManagementConfigurer.class,
                new SessionManagementConfigurer<HttpSecurity>().sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // csrf, http basic 비활성화
        http.csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);



        // 페이지 별 권한 설정
        http.authorizeHttpRequests((auth)->auth
                .requestMatchers("auth/**","/", "/home/**", "/cart/**", "/delivery/**",
                        "/order/**", "/orders/**", "/products/**","api/**", "account/**")
                        .permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/seller/**").hasAnyRole("ADMIN", "SELLER")

                // 그 외의 모든 요청은 인증 필요
        );

        http.logout((logout) -> logout.logoutUrl("/auth/logout")
                        .addLogoutHandler((request, response, auth) -> {
                            String kakaoAccessToken;

                            if (request.getCookies() != null) {
                                Optional<Cookie> tokenCookie = Arrays.stream(request.getCookies())
                                        .filter(
                                                cookie -> cookie.getName().equals("kakao")
                                        ).findFirst();

                                if (tokenCookie.isPresent()) {
                                    String token;
                                    try {
                                        token = URLDecoder.decode(tokenCookie.get().getValue(), "UTF-8");
                                    } catch (UnsupportedEncodingException e) {
                                        throw new RuntimeException(e);
                                    }
                                    if (token != null && token.startsWith("Bearer ")) {
                                        kakaoAccessToken = token.substring(7);
                                        kakaoApi.kakaoLogout(kakaoAccessToken);
                                        log.info("[kakaoLogout] 카카로 로그아웃 완료");
                                    }
                                }
                            }

                            for (Cookie cookie : request.getCookies()) {
                                String cookieName = cookie.getName();
                                Cookie cookieToDelete = new Cookie(cookieName, null);
                                cookieToDelete.setMaxAge(0);
                                cookieToDelete.setPath("/");
                                response.addCookie(cookieToDelete);
                            }
                        })
                        .logoutSuccessUrl("/auth/login")
                );

        return http.build();
    }

    // h2 console 관련 권한
    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled",havingValue = "true")
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }
}
