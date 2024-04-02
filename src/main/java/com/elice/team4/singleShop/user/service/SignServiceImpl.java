package com.elice.team4.singleShop.user.service;

import com.elice.team4.singleShop.user.dto.LogInResultDto;
import com.elice.team4.singleShop.user.dto.SignUpResultDto;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.jwt.JwtTokenProvider;
import com.elice.team4.singleShop.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class SignServiceImpl implements SignService{

    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SignServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public SignUpResultDto signUp(String password, String name, String email, String role) {
        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
        User user;
        if (role.equalsIgnoreCase("admin")) {
            user = User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(password))
//                    .roles(Collections.singletonList(password))
                    .build();
        } else {
            user = User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(password))
//                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new LogInResultDto();

        LOGGER.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과 주입");
        if (!savedUser.getName().isEmpty()) {
            LOGGER.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public LogInResultDto logIn(String name, String password) throws RuntimeException {
        LOGGER.info("[getSignInResult] signDataHandler로 회원 정보 요청");
        User user = userRepository.getByName(name);
        LOGGER.info("[getSignInResult] Id : {}", name);

        LOGGER.info("[getSignInResult] 패스워드 비교 수행");
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException();
        }

        LOGGER.info("[getSignInResult] 패스워드 일치");

        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        LogInResultDto logInResultDto = LogInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getName()),
                        user.getRole()))
                .build();

        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(logInResultDto);

        return logInResultDto;
    }

    private void setSuccessResult(SignUpResultDto result){
        result.setSuccess(true);
    }

    private void setFailResult(SignUpResultDto result){
        result.setSuccess(false);
    }
}
