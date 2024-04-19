package com.elice.team4.singleShop.order.repository;

import com.elice.team4.singleShop.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}