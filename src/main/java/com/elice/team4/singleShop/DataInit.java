package com.elice.team4.singleShop;

import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("init stub data");
//        userRepository.save(new User());
    }
}