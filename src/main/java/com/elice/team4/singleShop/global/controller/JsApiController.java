package com.elice.team4.singleShop.global.controller;

import com.elice.team4.singleShop.user.dto.PasswordDto;
import com.elice.team4.singleShop.user.dto.UserDto;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.jwt.JwtTokenProvider;
import com.elice.team4.singleShop.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@CrossOrigin(origins = "*")
public class JsApiController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userRepository.findByRoleIn(List.of("CONSUMER", "SELLER"));
        return ResponseEntity.ok(users);
    }

    // 왜 password와 findUser.getPassword가 맞지 않을까?
    @PostMapping("/users/password-check")
    public ResponseEntity<User> checkPassword (
            @CookieValue(value = "Authorization") String value,
            @RequestBody PasswordDto passwordDto) {

        String token = value.substring(7);
        log.info("토큰 값 : {}",token);

        UserDetails userDetailsInfo = jwtTokenProvider.getUserDetailsInfo(token);
        User userFindByName = jwtTokenProvider.getUserInfo(userDetailsInfo.getUsername());

        if(!passwordEncoder.matches(passwordDto.getPassword(), userFindByName.getPassword())) {
            throw new RuntimeException();
        }

        return ResponseEntity.ok(userFindByName);
    }


    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUsersRole(@PathVariable(name="id") Long id, @RequestParam(name = "role") User.Role role) {
        User findUser = userRepository.findById(id).orElseThrow();
        findUser.setRole(role);
        userRepository.save(findUser);
        return ResponseEntity.ok(findUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable(name="id") Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
