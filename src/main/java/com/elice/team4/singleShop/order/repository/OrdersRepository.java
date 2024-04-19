package com.elice.team4.singleShop.order.repository;

import com.elice.team4.singleShop.order.dto.OrdersDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersDto, Long> {
}
