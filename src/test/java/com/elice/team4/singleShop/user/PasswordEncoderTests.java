package com.elice.team4.singleShop.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void passwordEncode(){
        String password = "wawawow";
        String encodePassword = passwordEncoder.encode(password);
        System.out.println(encodePassword);
    }
}
