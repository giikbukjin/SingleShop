package com.elice.team4.singleShop.global.service;

import com.elice.team4.singleShop.user.entity.User;
import org.springframework.data.domain.Page;

public interface AdminPageService {
    public Page<User> getUserList(int page, int size);

}
