package com.elice.team4.singleShop.global.controller;

import com.elice.team4.singleShop.user.dto.ModiRoleDto;
import com.elice.team4.singleShop.user.dto.PasswordDto;
import com.elice.team4.singleShop.user.dto.UserDto;
import com.elice.team4.singleShop.user.dto.UserModifyDto;
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

    @GetMapping("/users/{id}")
    public ResponseEntity<UserModifyDto> getById(@PathVariable Long id) {

        User findUser = userRepository.findById(id).orElseThrow();
        UserModifyDto getUser = new UserModifyDto(findUser);

        return ResponseEntity.ok(getUser);
    }

    @GetMapping("/users/admin-check")
    public ResponseEntity<String> checkAdmin(
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
    public ResponseEntity<User> updateUsersRole(@PathVariable(name="id") Long id, @RequestBody ModiRoleDto modiRoleDto) {
        User findUser = userRepository.findById(id).orElseThrow();
        findUser.setRole(modiRoleDto.getRole());
        userRepository.save(findUser);
        return ResponseEntity.ok(findUser);
    }

    @PatchMapping("/users/edit/{id}")
    public ResponseEntity<UserModifyDto> modifyUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UserModifyDto userModifyDto) {
        User getUserById = userRepository.findById(id).orElseThrow();

        if(!passwordEncoder.matches(userModifyDto.getCurrentPassword(), getUserById.getPassword())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }
        if(userModifyDto.getName()!=null) {
            getUserById.setName(userModifyDto.getName());
        }
        if(userModifyDto.getPassword()!=null) {
            getUserById.setPassword(passwordEncoder.encode(userModifyDto.getPassword()));
        }
        if(userModifyDto.getAddress()!=null) {
            getUserById.setAddress(userModifyDto.mapToString(userModifyDto.getAddress()));
        }
        if(userModifyDto.getPhoneNumber()!=null) {
            getUserById.setPhoneNumber(userModifyDto.getPhoneNumber());
        }

        User savedUser = userRepository.save(getUserById);
        UserModifyDto newModifiedUserDto = new UserModifyDto(getUserById);

        return ResponseEntity.ok(newModifiedUserDto);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable(name="id") Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
