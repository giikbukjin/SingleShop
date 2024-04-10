package com.elice.team4.singleShop.user.service;

import com.elice.team4.singleShop.global.response.Response;
import com.elice.team4.singleShop.user.dto.LogInResultDto;
import com.elice.team4.singleShop.user.dto.SignUpResultDto;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.jwt.JwtTokenProvider;
import com.elice.team4.singleShop.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SignServiceImpl implements SignService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SignServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public SignUpResultDto signUp(String name, String password, String email, String role) {
        log.info("[getSignUpResult] 회원 가입 정보 전달");
        User user;
        if (role.equalsIgnoreCase("ADMIN")) {
            user = User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(User.Role.ADMIN)
                    .build();
        } else {
            user = User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(User.Role.CONSUMER)
                    .build();
        }

        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new LogInResultDto();

        log.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과 주입");
        if (!savedUser.getName().isEmpty()) {
            log.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            log.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public LogInResultDto logIn(String email, String password) throws RuntimeException {
        log.info("[getLogInResult] signDataHandler로 회원 정보 요청");
        User user = userRepository.findByEmail(email);
        log.info("[getSignInResult] Id : {}", email);

        log.info("[getLogInResult] 패스워드 비교 수행");
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException();
        }

        log.info("[getLogInResult] 패스워드 일치");

        log.info("[getLogInResult] LogInResultDto 객체 생성");
        LogInResultDto logInResultDto = LogInResultDto.builder()
                .token(jwtTokenProvider.createToken(user.getName(),
                        user.getRole()))
                .role(user.getRole())
                .build();

        log.info("[getLogInResult] LogInResultDto 객체에 값 주입");
        setSuccessResult(logInResultDto);

        return logInResultDto;
    }

    public void updateUser(Long id, String name, String password, String email) {
        log.info("[updateUser] 회원 정보 수정 시작. ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다. ID: " + id));

        log.info("[updateUser] 기존 회원 정보: {}", user);

        boolean passwordChanged = !passwordEncoder.matches(password, user.getPassword());
        if(!userRepository.existsByEmail(email) && !userRepository.existsByName(name)){
            user.update(name, passwordEncoder.encode(password), email);
        }else{
            throw new RuntimeException();
        }

        userRepository.save(user);
        LogInResultDto logInResultDto = new LogInResultDto();

        SignUpResultDto signUpResultDto = new SignUpResultDto();
        if (passwordChanged) {
            log.info("[updateUser] 비밀번호가 변경되었습니다. 새로운 JWT 토큰 발급합니다.");
            String newToken = jwtTokenProvider.createToken(String.valueOf(user.getName()), user.getRole());
            logInResultDto.setToken(newToken);
        }
        log.info("[updateUser] 회원 정보 업데이트 완료. ID: {}", id);
        setSuccessResult(signUpResultDto);

    }

    private void setSuccessResult(SignUpResultDto signUpResult){
        signUpResult.setSuccess(true);
        signUpResult.setCode(Response.SUCCESS.getCode());
        signUpResult.setMessage(Response.SUCCESS.getMessage());
    }

    private void setFailResult(SignUpResultDto signUpResult){
        signUpResult.setSuccess(false);
        signUpResult.setCode(Response.FAIL.getCode());
        signUpResult.setMessage(Response.FAIL.getMessage());
    }
}
