package com.elice.team4.singleShop.user.repository;

import com.elice.team4.singleShop.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByName(String name);
    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByName(String name);
}
