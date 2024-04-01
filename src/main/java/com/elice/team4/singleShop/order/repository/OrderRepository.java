package com.elice.team4.singleShop.order.repository;

import com.elice.team4.singleShop.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 이메일로 주문 필터링, 주문일자 내림차순 정렬해 주문 조회
    @Query("select o from Order o " +
            "where o.user.email = :email " +
            "order by o.orderDate desc")
    // 현재 로그인한 사용자의 주문을 이메일 기준으로 페이징 조건에 맞춰 조회
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Order o " +
            "where o.user.email = :email")
    Long countOrder(@Param("email") String email); // 현재 로그인한 회원의 주문 개수 조회
}
