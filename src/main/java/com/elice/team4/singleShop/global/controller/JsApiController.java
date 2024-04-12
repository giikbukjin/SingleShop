package com.elice.team4.singleShop.global.controller;

import com.elice.team4.singleShop.global.exception.UserNotFoundException;
import com.elice.team4.singleShop.user.dto.UserDto;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class JsApiController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userRepository.findByRoleIn(List.of("CONSUMER", "SELLER"));
        return ResponseEntity.ok(users);
    }

    // TODO : PATCHMAPPING("/users") : 역할 바뀌었을때 api 요청 > js
    // TODO : DELETEMAPPING("/users") : 삭제 시 api 요청 > js
    @PatchMapping("/users")
    public ResponseEntity<User> updateUsersRole(@RequestParam Long id, @RequestParam User.Role data) {
        User findUser = userRepository.findById(id).orElseThrow();
        findUser.setRole(data);
        userRepository.save(findUser);
        return ResponseEntity.ok(findUser);
    }

    @DeleteMapping("/users")
    public ResponseEntity deleteUser(@RequestParam Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
