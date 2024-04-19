package com.elice.team4.singleShop.user.service;

import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        log.info("[loadUserByUsername] loadUserByUsername 수행. username : {}", username);
        return userRepository.getByName(username);
    }

    public User loadUserInfoByUsername(String username) {
        log.info("[loadUserByUsername] loadUserByUsername 수행. username : {}", username);
        return userRepository.getByName(username);
    }

    public boolean checkUserByName(String username){
        log.info("[checkUserByName] 유저 이름을 통해 유저가 존재하는지 확인합니다.");
        return userRepository.existsByName(username);
    }
}
