package com.elice.team4.singleShop.global.controller;

import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@CrossOrigin(origins = "*")
public class JsApiController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userRepository.findByRoleIn(List.of("CONSUMER", "SELLER"));
        return ResponseEntity.ok(users);
    }

    // TODO : PATCHMAPPING("/users") : POSTMAN으로 했을 때 된다, JS로 잘 넘기면 끝
    // TODO : DELETEMAPPING("/users") : POSTMAN으로 했을 때 된다, JS로 잘 넘기면 끝
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
        return new ResponseEntity(HttpStatus.OK);
    }
}
