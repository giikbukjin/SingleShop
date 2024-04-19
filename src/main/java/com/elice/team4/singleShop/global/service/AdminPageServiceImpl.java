package com.elice.team4.singleShop.global.service;

import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPageServiceImpl implements AdminPageService {

    private final UserRepository userRepository;
    @Override
    public Page<User> getUserList(int page, int size) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("role"));
        sorts.add(Sort.Order.desc("name"));
        Pageable pageable = PageRequest.of(page, size, Sort.by(sorts));
        return userRepository.findAll(pageable);
    }
}
