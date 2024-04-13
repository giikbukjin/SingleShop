package com.elice.team4.singleShop.user.repository;

import com.elice.team4.singleShop.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByName(String name);
    User findByEmail(String email);
    List<User> findByRoleIn(List<String> role);
    Page<User> findAll(Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByName(String name);
}
