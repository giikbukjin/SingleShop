package com.elice.team4.singleShop.order.repository;

import com.elice.team4.singleShop.order.dto.OrderHistoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistoryDto, Long> {
}
