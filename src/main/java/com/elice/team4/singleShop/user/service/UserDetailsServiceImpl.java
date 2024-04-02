package com.elice.team4.singleShop.user.service;

import com.elice.team4.singleShop.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class)
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. username : {}", username);
        return userRepository.getByName(username);
    }
}
