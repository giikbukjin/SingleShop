package com.elice.team4.singleShop.order.repository;

import com.elice.team4.singleShop.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
